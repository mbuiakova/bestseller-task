package com.example.logic.service;

import com.example.logic.exceptions.CartNotFoundException;
import com.example.logic.exceptions.DrinkNotFoundException;
import com.example.logic.exceptions.ToppingNotFoundException;
import com.example.logic.model.BasicDrink;
import com.example.logic.model.Order;
import com.example.logic.model.Topping;
import com.example.logic.model.dto.DrinkWithToppings;
import com.example.logic.model.dto.Menu;
import com.example.logic.model.dto.Cart;
import com.example.logic.model.repo.OrderRepository;
import com.example.logic.model.repo.DrinkRepository;
import com.example.logic.model.repo.ToppingRepository;
import com.example.logic.model.repo.ToppingUsageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.data.util.Pair.toMap;

@Service
@AllArgsConstructor
public class OrderService {
    private static final Integer SESSION_DURATION_MINUTES = 5;
    private final OrderRepository orderRepository;
    private final DrinkRepository drinkRepository;
    private final ToppingRepository toppingRepository;
    private final ToppingUsageRepository toppingUsageRepository;

    private static final ExpiringMap<UUID, Cart> carts = ExpiringMap.builder()
            .expiration(SESSION_DURATION_MINUTES, TimeUnit.MINUTES)
            .build();

    /**
     * Attempts to find a drink with the provided name and add the specified toppings
     * in the desired amount.
     *
     * @param drinkName                 The name of the drink to get.
     * @param toppingsNamesWithQuantity A map of topping name and quantity to add to the drink.
     * @return A drink with toppings.
     * @throws DrinkNotFoundException   When a drink with the provided name can't be found.
     * @throws ToppingNotFoundException When one or more toppings can't be found.
     */
    public DrinkWithToppings getDrinkWithToppings(final String drinkName, final Map<String, Integer> toppingsNamesWithQuantity) {
        final Optional<BasicDrink> drinkOptional = drinkRepository.findByName(drinkName);

        if (drinkOptional.isEmpty()) {
            throw new DrinkNotFoundException(drinkName);
        }

        Map<Topping, Integer> toppings = null;

        if (toppingsNamesWithQuantity != null && !toppingsNamesWithQuantity.isEmpty()) {
            final var toppingsFound = toppingRepository.findByNameIn(toppingsNamesWithQuantity.keySet());

            if (toppingsFound.size() != toppingsNamesWithQuantity.size()) {
                final Set<String> notFoundToppingNames = new HashSet<>(toppingsNamesWithQuantity.keySet());
                notFoundToppingNames.retainAll(toppingsFound.stream().map(Topping::getName).collect(Collectors.toSet()));
                throw new ToppingNotFoundException(notFoundToppingNames);
            }

            toppings = toppingsNamesWithQuantity
                    .entrySet()
                    .stream()
                    .map(entry -> Pair.of(
                            toppingsFound
                                    .stream()
                                    .filter(t -> t.getName().equalsIgnoreCase(entry.getKey()))
                                    .findFirst()
                                    .get(),
                            entry.getValue()))
                    .collect(toMap());
        }

        return new DrinkWithToppings(drinkOptional.get(), toppings);
    }

    /**
     * Places an order, by processing the temporary user cart and saving
     * the order to the database, while also updating the statistics.
     *
     * @param cartId The user cart id to finalise the order for.
     * @return The order object.
     * @throws CartNotFoundException When a cart id is that of an invalid (not existing) cart.
     */
    @Transactional
    public Order placeOrder(final UUID cartId) {
        validateCartId(cartId);
        final Cart cart = getCartById(cartId);

        final Order saved = orderRepository.saveAndFlush(new Order(cart.getTotalPrice()));
        cart.getDrinks()
                .stream()
                .flatMap(d -> d.getToppings().entrySet().stream())
                .forEach(entry -> toppingUsageRepository.updateToppingUsagesByIdIncrementingUsage(
                        entry.getKey().getId(),
                        entry.getValue()));
        carts.remove(cartId);
        return saved;
    }

    /**
     * Returns a cart object by its id.
     *
     * @param cartId The ID of the cart to find.
     * @return The Cart object or null, if it can't be found by the provided ID.
     */
    public Cart getCartById(final UUID cartId) {
        if (cartId == null) {
            return null;
        }

        carts.resetExpiration(cartId);
        return carts.get(cartId);
    }

    /**
     * Validates that a cart exists with the ID passed.
     *
     * @param cartId the ID of the cart to validate.
     * @throws CartNotFoundException when a cart does not exist.
     */
    public void validateCartId(final UUID cartId) {
        if (getCartById(cartId) == null) {
            throw new CartNotFoundException(cartId);
        }
    }

    /**
     * Adds The provided drink to the cart with the provided id.
     *
     * @param cartId The cart id to add a drink to.
     * @param drink  A drink to add to the cart.
     * @return A cart to which the drink was added.
     */
    public Cart addDrinkToTheCart(UUID cartId, final DrinkWithToppings drink) {
        cartId = getCurrentOrNewCartIdAndResetExpiration(cartId);

        carts.compute(cartId, (k, v) -> {
            if (v == null) {
                v = new Cart(drink);
            } else {
                v.addDrink(drink);
            }
            return v;
        });

        return getCartById(cartId);
    }

    /**
     * Returns the menu: the available drinks and toppings.
     *
     * @return A menu object with all available drinks and toppings.
     */
    public Menu getMenu() {
        return new Menu(drinkRepository.findAll(), toppingRepository.findAll());
    }

    private Cart addNewCart() {
        final Cart cart = new Cart();
        carts.put(cart.getCartId(), cart);
        return cart;
    }

    private UUID getCurrentOrNewCartIdAndResetExpiration(final UUID cartId) {
        if (cartId == null) {
            return addNewCart().getCartId();
        } else if (!carts.containsKey(cartId)) {
            throw new CartNotFoundException(cartId);
        }

        carts.resetExpiration(cartId);
        return cartId;
    }
}

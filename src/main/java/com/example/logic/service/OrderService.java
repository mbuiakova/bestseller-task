package com.example.logic.service;

import com.example.logic.exceptions.CartNotFoundException;
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

    public DrinkWithToppings getDrinkWithToppings(final String drinkName, final Map<String, Integer> toppingsNamesWithQuantity) {
        final Optional<BasicDrink> drinkOptional = drinkRepository.findByName(drinkName);

        if (drinkOptional.isEmpty()) {
            // TODO throw a good exception
            return null;
        }

        Map<Topping, Integer> toppings = null;

        if (toppingsNamesWithQuantity != null && !toppingsNamesWithQuantity.isEmpty()) {
            final var toppingsFound = toppingRepository.findByNameIn(toppingsNamesWithQuantity.keySet());

            if (toppingsFound.size() != toppingsNamesWithQuantity.size()) {
                // TODO throw a good exception
                return null;
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

    @Transactional
    public Order putOrder(final UUID cartId) {
        final Cart cart = getCartById(cartId);

        if (cart == null) {
            throw new CartNotFoundException(cartId);
        }

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

    public Cart getCartById(final UUID cartId) {
        carts.resetExpiration(cartId);
        return carts.get(cartId);
    }

    public Cart addDrinkToTheCart(UUID cartId, final DrinkWithToppings drink) {
        cartId = getCurrentOrNewSessionIdAndResetExpiration(cartId);

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

    private Cart addNewCart() {
        final Cart cart = new Cart();
        carts.put(cart.getCartId(), cart);
        return cart;
    }

    public Menu getMenu() {
        return new Menu(drinkRepository.findAll(), toppingRepository.findAll());
    }

    private UUID getCurrentOrNewSessionIdAndResetExpiration(final UUID cartId) {
        if (cartId == null || !carts.containsKey(cartId)) {
            return addNewCart().getCartId();
        }

        carts.resetExpiration(cartId);
        return cartId;
    }
}

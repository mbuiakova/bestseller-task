package com.example.logic;

import com.example.logic.model.BasicDrink;
import com.example.logic.model.Topping;
import com.example.logic.model.dto.DrinkWithToppings;
import com.example.logic.model.dto.Menu;
import com.example.logic.model.dto.TemporaryCart;
import com.example.logic.model.repo.DrinkRepository;
import com.example.logic.model.repo.ToppingRepository;
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
    private DrinkRepository drinkRepository;
    private ToppingRepository toppingRepository;

    private static final ExpiringMap<UUID, TemporaryCart> temporaryCarts = ExpiringMap.builder()
            .expiration(SESSION_DURATION_MINUTES, TimeUnit.MINUTES)
            .build();

    public Optional<BasicDrink> getDrinkById(final UUID drinkId) {
        return drinkRepository.findById(drinkId);
    }

    public Optional<Topping> getToppingById(final UUID toppingId) {
        return toppingRepository.findById(toppingId);
    }

    public Optional<BasicDrink> getDrinkByName(final String drinkName) {
        return drinkRepository.findByName(drinkName);
    }

    public Optional<Topping> getToppingByName(final String toppingName) {
        return toppingRepository.findByName(toppingName);
    }

    public DrinkWithToppings getDrinkWithTopping(final String drinkName, final String toppingName) {
        return getDrinkWithToppings(drinkName, Map.of(toppingName, 1));
    }

    public DrinkWithToppings getDrinkWithToppings(final String drinkName, final Map<String, Integer> toppingsNamesWithQuantity) {
        Optional<BasicDrink> drinkOptional = drinkRepository.findByName(drinkName);

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

    private TemporaryCart createNewTemporaryCart() {
        final TemporaryCart temporaryCart = new TemporaryCart();
        temporaryCarts.put(temporaryCart.getCartId(), temporaryCart);
        return temporaryCart;
    }

    private UUID getCurrentOrNewSessionIdAndResetExpiration(final UUID cartId) {
        if (cartId == null || !temporaryCarts.containsKey(cartId)) {
            return createNewTemporaryCart().getCartId();
        }

        temporaryCarts.resetExpiration(cartId);
        return cartId;
    }

    public TemporaryCart getTemporaryCartById(final UUID cartId) {
        temporaryCarts.resetExpiration(cartId);
        return temporaryCarts.get(cartId);
    }

    public TemporaryCart addDrinkToTheCart(UUID cartId, final DrinkWithToppings drink) {
        cartId = getCurrentOrNewSessionIdAndResetExpiration(cartId);

        temporaryCarts.compute(cartId, (k, v) -> {
            if (v == null) {
                v = new TemporaryCart(drink);
            } else {
                v.addDrink(drink);
            }
            return v;
        });

        return getTemporaryCartById(cartId);
    }

    public TemporaryCart putOrder(final UUID cartId) {
        final TemporaryCart temporaryCart = getTemporaryCartById(cartId);
        // TODO save cart to database
        temporaryCarts.remove(cartId);
        return temporaryCart;
    }

    public Menu getMenu() {
        return new Menu(drinkRepository.findAll(), toppingRepository.findAll());
    }
}

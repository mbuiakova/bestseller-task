package com.example.logic.controller;

import com.example.logic.exceptions.DrinkNotFoundException;
import com.example.logic.exceptions.ToppingNotFoundException;
import com.example.logic.model.BasicDrink;
import com.example.logic.model.Topping;
import com.example.logic.model.ToppingWithUseCount;
import com.example.logic.service.DrinkService;
import com.example.logic.service.ToppingService;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ToppingService toppingService;
    private final DrinkService drinkService;

    @GetMapping("/getAllDrinks")
    public List<BasicDrink> getAllDrinks() {
        return drinkService.getAllDrinks();
    }

    @GetMapping("/getDrink/{id}")
    public BasicDrink getDrinkById(@NonNull @PathVariable final UUID id) {
        final BasicDrink drink = drinkService.getDrinkById(id).orElse(null);

        if (drink == null) {
            throw new DrinkNotFoundException(id);
        }

        return drink;
    }

    @PostMapping("/updateOrCreateDrink")
    public BasicDrink updateOrCreateDrink(@NonNull @RequestBody final BasicDrink drink) {
        return drinkService.saveDrink(drink);
    }

    @DeleteMapping("/deleteDrink/{id}")
    public void deleteDrinkById(@NonNull @PathVariable final UUID id) {
        drinkService.deleteDrink(id);
    }

    @GetMapping("/getAllToppings")
    public List<Topping> getAllToppings() {
        return toppingService.getAllToppings();
    }

    @GetMapping("/getTopping/{id}")
    public Topping getToppingById(@NonNull @PathVariable final UUID id) {
        final Topping topping = toppingService.getToppingById(id).orElse(null);

        if (topping == null) {
            throw new ToppingNotFoundException(id);
        }

        return topping;
    }

    @PostMapping("/updateOrCreateTopping")
    public Topping updateOrCreateDrink(@NonNull @RequestBody final Topping topping) {
        return toppingService.saveTopping(topping);
    }

    @DeleteMapping("/deleteTopping/{id}")
    public void deleteToppingById(@NonNull @PathVariable final UUID id) {
        toppingService.deleteTopping(id);
    }

    @GetMapping("/getTopUsedToppings")
    public Collection<ToppingWithUseCount> getTopUsedToppings() {
        return toppingService.getTopToppings();
    }
}

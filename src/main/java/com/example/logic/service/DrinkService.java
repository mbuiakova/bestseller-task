package com.example.logic.service;

import com.example.logic.model.BasicDrink;
import com.example.logic.model.repo.DrinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;

    /**
     * @return All the drinks available.
     */
    public List<BasicDrink> getAllDrinks() {
        return drinkRepository.findAll();
    }

    /**
     * Attempts to find a specific drink by id.
     * @param id The id of a drink to find.
     * @return A drink object if found; an empty [Optional] otherwise.
     */
    public Optional<BasicDrink> getDrinkById(final UUID id) {
        return drinkRepository.findById(id);
    }

    /**
     * Attempts to save the drink passed in the database.
     * @param drink A drink to store (create or update).
     * @return The saved drink, never null.
     */
    public BasicDrink saveDrink(final BasicDrink drink) {
        return drinkRepository.save(drink);
    }

    /**
     * Attempts to delete a drink by its ID.
     * @param id The id of the drink to delete.
     */
    public void deleteDrink(final UUID id) {
        drinkRepository.deleteById(id);
    }
}
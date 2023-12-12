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

    public List<BasicDrink> getAllDrinks() {
        return drinkRepository.findAll();
    }

    public Optional<BasicDrink> getDrinkById(final UUID id) {
        return drinkRepository.findById(id);
    }

    public BasicDrink saveDrink(final BasicDrink drink) {
        return drinkRepository.save(drink);
    }

    public void deleteDrink(final UUID id) {
        drinkRepository.deleteById(id);
    }
}
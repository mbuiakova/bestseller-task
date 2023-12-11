package com.example.logic;

import com.example.logic.model.BasicDrink;
import com.example.logic.model.Topping;
import com.example.logic.model.dto.DrinkWithTopping;
import com.example.logic.model.repo.DrinkRepository;
import com.example.logic.model.repo.ToppingRepository;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {

    private DrinkRepository repository;
    private ToppingRepository toppingRepository;

    private static Map<String, Double> crunchifyMap = ExpiringMap.builder()
            .expiration(5, TimeUnit.MINUTES)
            .build();


    public DrinkWithTopping getDrink(String drinkName, String toppingName) {
        Optional<BasicDrink> drinkOptional = repository.findByName(drinkName);
        if (drinkOptional.isEmpty()) {
            return null;
        }
        Optional<Topping> toppingOptional = toppingRepository.findByName(toppingName);
        return toppingOptional.map(topping -> new DrinkWithTopping(drinkOptional.get(), topping)).orElse(null);


    }
}

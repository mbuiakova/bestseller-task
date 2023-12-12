package com.example.logic.service;

import com.example.logic.model.Topping;
import com.example.logic.model.ToppingWithUseCount;
import com.example.logic.model.repo.ToppingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ToppingService {
    private final ToppingRepository toppingRepository;

    public Collection<ToppingWithUseCount> getTopToppings() {
        return toppingRepository
                .findAllByOrderByUsedCountDesc()
                .stream()
                .map(t -> new ToppingWithUseCount((Topping) t.get(0), (Integer) t.get(1)))
                .toList();
    }

    public List<Topping> getAllToppings() {
        return toppingRepository.findAll();
    }

    public Optional<Topping> getToppingById(final UUID id) {
        return toppingRepository.findById(id);
    }

    public Topping saveTopping(final Topping topping) {
        return toppingRepository.save(topping);
    }

    public void deleteTopping(final UUID id) {
        toppingRepository.deleteById(id);
    }
}
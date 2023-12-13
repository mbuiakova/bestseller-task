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

    /**
     * @return A collection of all the topping available, with the usage count. The toppings
     * are sorted by the usage count in the descending order.
     */
    public Collection<ToppingWithUseCount> getTopToppings() {
        return toppingRepository
                .findAllByOrderByUsedCountDesc()
                .stream()
                .map(t -> new ToppingWithUseCount((Topping) t.get(0), (Integer) t.get(1)))
                .toList();
    }

    /**
     * @return All the toppings available.
     */
    public List<Topping> getAllToppings() {
        return toppingRepository.findAll();
    }

    /**
     * Attempts to find a specific topping by id.
     * @param id The id of a topping to find.
     * @return A topping object if found; an empty [Optional] otherwise.
     */
    public Optional<Topping> getToppingById(final UUID id) {
        return toppingRepository.findById(id);
    }

    /**
     * Attempts to save the topping passed in the database.
     * @param topping A topping to store (create or update).
     * @return The saved topping, never null.
     */
    public Topping saveTopping(final Topping topping) {
        return toppingRepository.save(topping);
    }

    /**
     * Attempts to delete a topping by its ID.
     * @param id The id of the topping to delete.
     */
    public void deleteTopping(final UUID id) {
        toppingRepository.deleteById(id);
    }
}
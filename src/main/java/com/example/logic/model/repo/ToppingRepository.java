package com.example.logic.model.repo;

import com.example.logic.model.Topping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ToppingRepository extends JpaRepository<Topping, UUID> {

    Optional<Topping> findByName(final String name);
    Set<Topping> findByNameIn(final Set<String> names);
    Optional<Topping> findById(final UUID id);
}

package com.example.logic.model.repo;

import com.example.logic.model.BasicDrink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DrinkRepository extends JpaRepository<BasicDrink, UUID> {

    Optional<BasicDrink> findByName(final String name);
    Optional<BasicDrink> findById(final UUID id);
}

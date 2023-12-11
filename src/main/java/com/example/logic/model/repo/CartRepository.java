package com.example.logic.model.repo;

import com.example.logic.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    Optional<Cart> findById(final UUID id);
    List<Cart> findAll();
}

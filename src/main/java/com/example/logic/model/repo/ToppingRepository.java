package com.example.logic.model.repo;

import com.example.logic.model.Topping;
import com.example.logic.model.ToppingUsage;
import com.example.logic.model.ToppingWithUseCount;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ToppingRepository extends JpaRepository<Topping, UUID> {

    Optional<Topping> findByName(final String name);
    Set<Topping> findByNameIn(final Set<String> names);
    Optional<Topping> findById(final UUID id);

    @Query("SELECT t, tu.usedCount FROM Topping t LEFT JOIN ToppingUsage tu ON t.id = tu.toppingId ORDER BY tu.usedCount DESC")
    List<Tuple> findAllByOrderByUsedCountDesc();
}

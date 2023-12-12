package com.example.logic.model.repo;

import com.example.logic.model.ToppingUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ToppingUsageRepository extends JpaRepository<ToppingUsage, UUID> {
    Optional<ToppingUsage> findByToppingId(final UUID id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ToppingUsage t SET t.usedCount = t.usedCount + :increment WHERE t.toppingId = :toppingId")
    void updateToppingUsagesByIdIncrementingUsage(final UUID toppingId, final int increment);
}

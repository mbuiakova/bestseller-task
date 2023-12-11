package com.example.logic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "drink")
public class BasicDrink {

    @Id
    private UUID id;

    private String name;
    private BigDecimal price;
}

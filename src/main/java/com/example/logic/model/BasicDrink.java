package com.example.logic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * An entity for the drink table.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "drink")
public class BasicDrink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private BigDecimal price;
}

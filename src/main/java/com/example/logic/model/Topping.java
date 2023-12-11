package com.example.logic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "topping")
public class Topping implements Serializable {

    @JsonIgnore
    @Id
    private UUID id;

    private String name;
    private BigDecimal price;

    public Topping(final String name, final BigDecimal price) {
        this(UUID.randomUUID(), name, price);
    }
}

package com.example.logic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Encapsulates a topping with its use count.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ToppingWithUseCount implements Serializable {
    private Topping topping;
    private Integer usedCount;
}

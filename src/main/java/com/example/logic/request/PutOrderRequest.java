package com.example.logic.request;

import com.example.logic.exceptions.BadPutOrderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutOrderRequest implements Serializable {

    private UUID cartId;

    public void validate() {
        if (cartId == null) {
            throw new BadPutOrderRequest();
        }
    }
}

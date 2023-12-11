package com.example.utils.json;

import com.example.logic.model.Topping;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
class ToppingWithQuantity implements Serializable{
    private final Topping topping;
    private final Integer quantity;
}

public class ToppingMapSerializer
        extends JsonSerializer<Map<Topping, Integer>>
{
    @Override
    public void serialize(
            final Map<Topping, Integer> values,
            final JsonGenerator g,
            final SerializerProvider provider)
            throws IOException
    {
        g.writeStartObject();
        for (var entry : values.entrySet()) {
            final var toppingWithQuantity = new ToppingWithQuantity(entry.getKey(), entry.getValue());
            g.writeObjectField(entry.getKey().getName(), toppingWithQuantity);
        }
        g.writeEndObject();
    }
}
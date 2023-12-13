package com.example.utils.json;

import com.example.logic.model.discounts.Discount;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Serialises the discount object as a string from its description.
 */
public class DiscountSerializer
        extends JsonSerializer<Discount>
{
    @Override
    public void serialize(
            final Discount value,
            final JsonGenerator g,
            final SerializerProvider provider)
            throws IOException
    {
        g.writeString(value.getDescription());
    }
}
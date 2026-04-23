package com.ecommerce.backend.model.converter;

import com.ecommerce.backend.model.vo.ShippingAddress;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter
public class ShippingAddressListConverter implements AttributeConverter<List<ShippingAddress>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ShippingAddress> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JacksonException e) {
            throw new RuntimeException("Error serializing ShippingAddress list to JSON", e);
        }
    }

    @Override
    public List<ShippingAddress> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank() || "[]".equals(dbData)) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<ShippingAddress>>() {});
        } catch (JacksonException e) {
            throw new RuntimeException("Error deserializing JSON to ShippingAddress list", e);
        }
    }
}

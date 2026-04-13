package com.ecommerce.backend.controller.mvc;

import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public abstract class BaseManagementController {

    protected final ObjectMapper objectMapper;

    protected BaseManagementController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected java.util.List<Map<String, Object>> toMapList(java.util.List<?> items) {
        return items.stream()
                .map(item -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = objectMapper.convertValue(item, Map.class);
                    return map;
                })
                .toList();
    }

    protected String requiredText(Map<String, String> formData, String key) {
        String value = optionalText(formData, key);
        if (value == null) {
            throw new IllegalArgumentException("El campo " + key + " es obligatorio.");
        }
        return value;
    }

    protected String optionalText(Map<String, String> formData, String key) {
        String value = formData.get(key);
        System.out.println("Extracted value for key '" + key + "': '" + value + "'"); // Debugging line
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    protected Long requiredLong(Map<String, String> formData, String key) {
        try {
            String value = requiredText(formData, key);
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + key + " debe ser numerico.");
        }
    }

    protected int requiredInt(Map<String, String> formData, String key) {
        try {
            String value = requiredText(formData, key);
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + key + " debe ser numerico.");
        }
    }

    protected double requiredDouble(Map<String, String> formData, String key) {
        try {
            String value = requiredText(formData, key);
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + key + " debe ser decimal.");
        }
    }

    protected boolean checkbox(Map<String, String> formData, String key) {
        return formData.containsKey(key);
    }

    protected String safeText(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

}

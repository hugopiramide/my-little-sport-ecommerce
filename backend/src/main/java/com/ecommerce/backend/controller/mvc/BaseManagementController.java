package com.ecommerce.backend.controller.mvc;

import org.springframework.web.util.UriComponentsBuilder;

import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
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

    protected String buildPaginationUrl(String path, int page, int size, Map<String, ?> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(path)
                .queryParam("page", page)
                .queryParam("size", size);
        if (params != null) {
            params.forEach((key, value) -> {
                if (value != null) {
                    builder.queryParam(key, value);
                }
            });
        }
        return builder.toUriString();
    }

    protected Map<String, Object> paginationParams(Object... keyValues) {
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i + 1 < keyValues.length; i += 2) {
            String key = (String) keyValues[i];
            Object value = keyValues[i + 1];
            if (value != null) {
                params.put(key, value);
            }
        }
        return params;
    }

    protected String requiredText(Map<String, String> formData, String key) {
        String value = optionalText(formData, key);
        if (value == null) {
            throw new IllegalArgumentException("The field " + key + " is required.");
        }
        return value;
    }

    protected String optionalText(Map<String, String> formData, String key) {
        String value = formData.get(key);
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
            throw new IllegalArgumentException("The field " + key + " must be numeric.");
        }
    }

    protected int requiredInt(Map<String, String> formData, String key) {
        try {
            String value = requiredText(formData, key);
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("The field " + key + " must be numeric.");
        }
    }

    protected double requiredDouble(Map<String, String> formData, String key) {
        try {
            String value = requiredText(formData, key);
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("The field " + key + " must be a decimal number.");
        }
    }

    protected boolean checkbox(Map<String, String> formData, String key) {
        return formData.containsKey(key);
    }

    protected String safeText(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

}

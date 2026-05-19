package com.ecommerce.backend.dto.response;

public record ShippingAddressResponseDTO(
    int id,
    Long userId,
    String addressName,
    String recipientName,
    String companyName,
    String street,
    String addressLine2,
    String city,
    String state,
    String postalCode,
    String countryCode,
    String phoneNumber,
    String deliveryInstructions
) {}

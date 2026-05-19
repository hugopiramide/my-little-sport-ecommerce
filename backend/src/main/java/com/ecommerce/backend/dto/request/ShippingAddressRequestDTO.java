package com.ecommerce.backend.dto.request;

public record ShippingAddressRequestDTO(
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

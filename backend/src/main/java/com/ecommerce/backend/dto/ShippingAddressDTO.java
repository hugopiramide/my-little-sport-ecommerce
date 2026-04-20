package com.ecommerce.backend.dto;

public record ShippingAddressDTO(
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

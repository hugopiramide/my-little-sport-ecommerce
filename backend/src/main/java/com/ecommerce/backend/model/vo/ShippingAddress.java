package com.ecommerce.backend.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingAddress {

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "street")
    private String street;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country_code", length = 5)
    private String countryCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "delivery_instructions")
    private String deliveryInstructions;

}

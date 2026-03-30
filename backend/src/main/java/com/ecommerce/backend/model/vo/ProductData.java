package com.ecommerce.backend.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class ProductData {

    @Column(length = 100)
    private String name;

    private String description;

    @Column(name = "base_price")
    private double basePrice = 0.00;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    public ProductData(String name, String description, double basePrice, String imageUrl) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.imageUrl = imageUrl;
    }

}

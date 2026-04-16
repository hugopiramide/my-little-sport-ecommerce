package com.ecommerce.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Long quantity = 1L;

    @Column(name = "price_at_purchase")
    private double price_at_purchase = 0.0;

    // Snapshot
    @Column(name = "product_name", length = 255)
    private String productName;

    @Column(name = "product_size", length = 50)
    private String productSize;

    @Column(name = "product_image_url", length = 500)
    private String productImageUrl;

    @Column(name = "base_price")
    private double basePrice = 0.0;

    @Column(name = "price_modifier")
    private double priceModifier = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = true)
    private ProductVariant productVariant;
}


package com.ecommerce.backend.controller.rest;

import com.ecommerce.backend.dto.PaymentRequestDTO;
import com.ecommerce.backend.service.PayPalService;
import com.ecommerce.backend.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.paypal.orders.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final StripeService stripeService;
    private final PayPalService payPalService;

    @PostMapping("/stripe/create-intent")
    public ResponseEntity<Map<String, String>> createStripePaymentIntent(@RequestBody PaymentRequestDTO request) {
        try {
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(request);
            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/paypal/create-order")
    public ResponseEntity<Map<String, String>> createPayPalOrder(@RequestBody PaymentRequestDTO request) {
        try {
            Order order = payPalService.createOrder(request);
            Map<String, String> response = new HashMap<>();
            response.put("orderId", order.id());
            
            order.links().stream()
                .filter(link -> "approve".equals(link.rel()))
                .findFirst()
                .ifPresent(link -> response.put("approveUrl", link.href()));

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/paypal/capture-order/{orderId}")
    public ResponseEntity<Map<String, Object>> capturePayPalOrder(@PathVariable String orderId) {
        try {
            Order order = payPalService.captureOrder(orderId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", order.status());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}

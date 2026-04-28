package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.PaymentRequestDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeService {

    public PaymentIntent createPaymentIntent(PaymentRequestDTO paymentRequest) throws StripeException {
        long amountInCents = paymentRequest.amount().multiply(new BigDecimal("100")).longValue();

        PaymentIntentCreateParams params =
          PaymentIntentCreateParams.builder()
            .setAmount(amountInCents)
            .setCurrency(paymentRequest.currency() != null ? paymentRequest.currency().toUpperCase() : "EUR")
            .setDescription(paymentRequest.description())
            .setAutomaticPaymentMethods(
              PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                .setEnabled(true)
                .build()
            )
            .build();

        return PaymentIntent.create(params);
    }
}

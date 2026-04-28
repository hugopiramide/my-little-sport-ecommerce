package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.PaymentRequestDTO;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayPalService {

    private final PayPalHttpClient payPalHttpClient;

    public Order createOrder(PaymentRequestDTO paymentRequest) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode(paymentRequest.currency() != null ? paymentRequest.currency().toUpperCase() : "EUR")
                        .value(paymentRequest.amount().toString()));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        
        request.requestBody(orderRequest);
        
        HttpResponse<Order> response = payPalHttpClient.execute(request);
        return response.result();
    }

    public Order captureOrder(String orderId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest());
        HttpResponse<Order> response = payPalHttpClient.execute(request);
        return response.result();
    }
}

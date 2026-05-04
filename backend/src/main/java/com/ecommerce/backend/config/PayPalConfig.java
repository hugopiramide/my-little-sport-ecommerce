package com.ecommerce.backend.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.paypal")
@Getter
@Setter
public class PayPalConfig {

    private String clientId;

    private String clientSecret;

    private String mode;

    @Bean
    public PayPalHttpClient payPalHttpClient() {
        PayPalEnvironment environment;
        if ("live".equalsIgnoreCase(mode)) {
            environment = new PayPalEnvironment.Live(clientId, clientSecret);
        } else {
            environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
        }
        return new PayPalHttpClient(environment);
    }
}

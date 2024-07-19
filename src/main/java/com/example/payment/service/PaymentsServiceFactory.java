package com.example.payment.service;

import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class PaymentsServiceFactory {

    private PaymentsService paymentsService;

    public PaymentsService getPaymentService(String payType) {
        switch (payType.toUpperCase()) {
            default:
                throw new IllegalArgumentException("Invalid Payment Type.");
        }
    }

}
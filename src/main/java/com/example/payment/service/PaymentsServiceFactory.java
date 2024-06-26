package com.example.payment.service;

import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class PaymentsServiceFactory {

    private PaymentsService paymentsService;

    public PaymentsService getPaymentService(String payType) {
        switch (payType.toUpperCase().substring(0, 5)) {
            case "KAKAO":
                return paymentsService;
            default:
                throw new IllegalArgumentException("Invalid Payment Type.");
        }
    }

}
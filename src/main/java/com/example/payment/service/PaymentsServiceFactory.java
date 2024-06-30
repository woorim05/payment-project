package com.example.payment.service;

import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class PaymentsServiceFactory {

    @Resource(name = "KakaoPayService")
    private KakaoPayService kakaoPayService;

    public PaymentsService getPaymentService(String payType) {
        switch (payType.toUpperCase().substring(0, 5)) {
            case "KAKAO":
                return kakaoPayService;
            default:
                throw new IllegalArgumentException("Invalid Payment Type.");
        }
    }

}
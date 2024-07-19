package com.example.payment.dto;

import com.example.payment.entity.Payments;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class PaymentsDto {
    private Long idx;
    private String userId;
    private String orderId;
    private String transactionId;
    private String payMethod;
    private Map<String, Object> responseData;

    public static PaymentsDto toDto(Payments payments) {
        return  PaymentsDto.builder()
                .idx(payments.getIdx())
                .userId(payments.getUserId())
                .orderId(payments.getOrderId())
                .transactionId(payments.getTransactionId())
                .payMethod(payments.getPayMethod())
                .responseData(payments.getResponseData())
                .build();
    }
}

package com.example.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDto {
    private String userId;      // 사용자 ID
    private String orderId;     // 주문번호
    private String payMethod;   // 결제수단
    private String productName; // 대표상품명
    private int productCnt;     // 상품 수량
    private int totalPayAmount; // 총 결제 금액
    private int taxFreeAmount;  // 과세금액
}

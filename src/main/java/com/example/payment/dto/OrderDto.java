package com.example.payment.dto;

import lombok.Data;

@Data
public class OrderDto {
    private String orderId;
    private String userId;
    private String itemName;
    private Integer totalQuntity;
    private Integer totalAmount;
    private Integer taxFreeAmount;

    // kakao
    private String kakaoCid;
    private String kakaoTid;
    private String kakaoToken;
}

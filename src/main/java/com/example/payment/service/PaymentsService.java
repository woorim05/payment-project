package com.example.payment.service;

import com.example.payment.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class PaymentsService {
    public OrderDto getOrderInfo(String orderId) {
        OrderDto orderInfo = new OrderDto();

        // dummy data
        orderInfo.setOrderId(orderId);
        orderInfo.setUserId("testUser");
        orderInfo.setItemName("아이템 이름");
        orderInfo.setTotalQuntity(1);
        orderInfo.setTotalAmount(20000);
        orderInfo.setTaxFreeAmount(0);

        // 가맹점 ID - DB 조회
        orderInfo.setKakaoCid("TC0ONETIME");

        return orderInfo;
    }

    public abstract Map<String, Object> ready(OrderDto orderInfo) throws Exception;

    public abstract Map<String, Object> approve(Map<String, Object> orderInfo) throws Exception;

    public abstract Map<String, Object> cancel(OrderDto orderInfo) throws Exception;
}

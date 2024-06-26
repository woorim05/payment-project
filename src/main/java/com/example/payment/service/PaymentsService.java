package com.example.payment.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public abstract class PaymentsService {
    public Map<String, Object> getOrderInfo(String orderId) {
        Map<String, Object> orderInfo = new HashMap<>();

        // dummy data
        orderInfo.put("orderId", orderId);
        orderInfo.put("urserid", "testUser");

        return orderInfo;
    }

    public abstract Map<String, Object> ready(Map<String, Object> orderInfo) throws Exception;

    public abstract Map<String, Object> approve(Map<String, Object> orderInfo) throws Exception;

    public abstract Map<String, Object> cancel(Map<String, Object> orderInfo) throws Exception;
}

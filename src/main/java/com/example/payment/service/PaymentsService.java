package com.example.payment.service;

import com.example.payment.dto.OrderDto;
import com.example.payment.dto.PaymentsDto;
import com.example.payment.entity.Payments;
import com.example.payment.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class PaymentsService {
    @Autowired
    private PaymentsRepository paymentsRepository;

    /**
     * 결제 요청
     * @param orderInfo
     * @return
     */
    public abstract Map<String, Object> ready(OrderDto orderInfo) throws Exception;

    /**
     * 승인 요청
     * @param paramMap
     * @return
     */
    public abstract PaymentsDto approve(Map<String, Object> paramMap) throws Exception;

    /**
     * 취소 요청
     *
     * @param paymentsInfo
     * @return
     */
    public abstract Map<String, Object> cancel(PaymentsDto paymentsInfo) throws Exception;

    /**
     * 주문 정보 조회
     * @param orderId
     * @return
     */
    public OrderDto getOrderInfo(String orderId) {
        // TODO: redis/db 에서 주문 정보 읽어오기

        // dummy data
        OrderDto orderDto = OrderDto.builder()
                .userId("testUser")
                .orderId(orderId)
                .productName("상품명")
                .productCnt(1)
                .totalPayAmount(1000)
                .taxFreeAmount(0)
                .build();

        return orderDto;
    }

    /**
     * 결제 정보 조회
     * @param orderId
     * @return
     */
    public PaymentsDto getPaymentsInfo(String payMethod, String orderId) {
        Payments paymentsInfo = paymentsRepository.findByPayMethodAndOrderId(payMethod, orderId);
        return PaymentsDto.toDto(paymentsInfo);
    }
}

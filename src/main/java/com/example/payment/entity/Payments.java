package com.example.payment.entity;

import com.example.payment.dto.PaymentsDto;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Map;

@Getter
@Entity
@Table(name = "paymentsDto_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(name = "order_id", nullable = false, length = 20)
    private String orderId;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "pay_method", nullable = false)
    private String payMethod;

    @Type(JsonBinaryType.class)
    @Column(name = "response_data", nullable = false, columnDefinition = "jsonb")
    private Map<String, Object> responseData;

    public static Payments toEntity(PaymentsDto paymentsDto) {
        return  Payments.builder()
                .idx(paymentsDto.getIdx())
                .userId(paymentsDto.getUserId())
                .orderId(paymentsDto.getOrderId())
                .transactionId(paymentsDto.getTransactionId())
                .payMethod(paymentsDto.getPayMethod())
                .responseData(paymentsDto.getResponseData())
                .build();
    }
}

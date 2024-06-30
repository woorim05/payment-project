package com.example.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

public class KakaoPayDto {
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record readyReq(
        String cid,
        String partner_order_id,
        String partner_user_id,
        String item_name,
        int quantity,
        int total_amount,
        int tax_free_amount,
        String approval_url,
        String cancel_url,
        String fail_url
    ) {}

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record approveReq(
        String cid,
        String tid,
        String partner_order_id,
        String partner_user_id,
        String pg_token
    ) {}

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record cancelReq(
            String cid,
            String tid,
            int cancel_amount,
            int cancel_tax_free_amount,
            String pg_token
    ) {}
}
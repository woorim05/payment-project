package com.example.payment.service;

import com.example.payment.dto.KakaoPayDto;
import com.example.payment.dto.OrderDto;
import com.example.payment.feignClient.KakaoFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;


@Log4j2
@RequiredArgsConstructor
@Service(value = "KakaoPayService")
public class KakaoPayService extends PaymentsService {

    @Value("${payments.kakao.secretKey}")
    private String SECRET_KEY;

    @Value("${payments.redirectUrl}")
    private String REDIRECT_URL;

    private final KakaoFeignClient feignClient;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> ready(OrderDto orderInfo) throws Exception {
        Map<String, Object> result;

        KakaoPayDto.readyReq readyReq = KakaoPayDto.readyReq.builder()
                .cid("TC0ONETIME") // 가맹점 id
                .partner_order_id(orderInfo.getOrderId())
                .partner_user_id(orderInfo.getUserId())
                .item_name(orderInfo.getItemName())
                .quantity(orderInfo.getTotalQuntity())
                .total_amount(orderInfo.getTotalAmount())
                .tax_free_amount(orderInfo.getTaxFreeAmount())
                .approval_url(REDIRECT_URL + "?resultCode=Success")
                .cancel_url(REDIRECT_URL + "?resultCode=UserCancel")
                .fail_url(REDIRECT_URL + "?resultCode=Fail")
                .build();

        JSONObject responseEntity = feignClient.ready("SECRET_KEY " + SECRET_KEY, readyReq);
        result = objectMapper.readValue(responseEntity.toString(), Map.class);

        log.info("KAKAO PAY READY RESULT :: {}", result.toString());

        return result;
    }

    @Override
    public Map<String, Object> approve(Map<String, Object> orderInfo) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> cancel(Map<String, Object> orderInfo) throws Exception {
        return null;
    }
}
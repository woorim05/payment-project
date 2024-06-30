package com.example.payment.service;

import com.example.payment.dto.KakaoPayDto;
import com.example.payment.dto.OrderDto;
import com.example.payment.enums.ServiceErrorCode;
import com.example.payment.exception.ServiceException;
import com.example.payment.feignClient.KakaoFeignClient;
import com.example.payment.repository.RedisDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Log4j2
@RequiredArgsConstructor
@Service(value = "KakaoPayService")
public class KakaoPayService extends PaymentsService {

    @Value("${payments.kakao.secretKey}")
    private String SECRET_KEY;

    @Value("${payments.redirectUrl}")
    private String REDIRECT_URL;

    private final RedisDao redisDao;
    private final KakaoFeignClient feignClient;
    private ObjectMapper objectMapper = new ObjectMapper();

    private final String REDIS_PAYMENTS_KEY = "user:payments:";

    @Override
    public Map<String, Object> ready(OrderDto orderInfo) throws Exception {
        Map<String, Object> result;

        KakaoPayDto.readyReq readyReq = KakaoPayDto.readyReq.builder()
                .cid(orderInfo.getKakaoCid())
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

        try {
            // redis 저장
            Map<String, String> approveReq = new HashMap<>();
            approveReq.put("tid", result.get("tid").toString());
            approveReq.put("cid", orderInfo.getKakaoCid()); // TODO: orderInfo.get("cid").toString()
            approveReq.put("orderId", orderInfo.getOrderId());

            String data = objectMapper.writeValueAsString(approveReq);
            redisDao.setValue(REDIS_PAYMENTS_KEY + orderInfo.getUserId(), data);

        } catch (Exception e) {
            log.error("KAKAO PAY READY :: Failed to save to redis :: {}", orderInfo.getOrderId());
            e.printStackTrace();
            throw new ServiceException(ServiceErrorCode.API_RESPONSE_FAIL);
        }

        return result;
    }

    @Override
    public Map<String, Object> approve(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> result;
        String userId = paramMap.get("userId").toString();

        String paymentsInfo = redisDao.getValue(REDIS_PAYMENTS_KEY + userId);
        Map<String, String> paymentsInfoJson = objectMapper.readValue(paymentsInfo, Map.class);

        KakaoPayDto.approveReq approveReq = KakaoPayDto.approveReq.builder()
                .cid(paymentsInfoJson.get("cid"))
                .tid(paymentsInfoJson.get("tid"))
                .partner_order_id(paymentsInfoJson.get("orderId"))
                .partner_user_id(userId)
                .pg_token(paramMap.get("pg_token").toString())
                .build();

        JSONObject responseEntity = feignClient.approve("SECRET_KEY " + SECRET_KEY, approveReq);
        result = objectMapper.readValue(responseEntity.toString(), Map.class);

        log.info("KAKAO PAY APPROVE RESULT :: {}", result.toString());

        return result;
    }

    @Override
    public Map<String, Object> cancel(OrderDto cancelDto) throws Exception {
        Map<String, Object> result;

        KakaoPayDto.cancelReq cancelReq = KakaoPayDto.cancelReq.builder()
                .cid(cancelDto.getKakaoCid())
                .tid(cancelDto.getKakaoTid())
                .cancel_amount(cancelDto.getTotalAmount())
                .cancel_tax_free_amount(cancelDto.getTaxFreeAmount())
                .pg_token(cancelDto.getKakaoToken())
                .build();

        JSONObject responseEntity = feignClient.cancel("SECRET_KEY " + SECRET_KEY, cancelReq);
        result = objectMapper.readValue(responseEntity.toString(), Map.class);

        log.info("KAKAO PAY CACEL RESULT :: {}", result.toString());

        return result;
    }
}
package com.example.payment.feignClient;

import com.example.payment.config.FeignConfig;
import com.example.payment.dto.KakaoPayDto;
import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoFeignClient", url="${payments.kakao.url}", configuration = FeignConfig.class)
public interface KakaoFeignClient {

    @PostMapping(value = "/ready")
    JSONObject ready(@RequestHeader("Authorization") String secretKey, KakaoPayDto.readyReq readyReq);

    @PostMapping(value = "/approve")
    JSONObject approve(@RequestHeader("Authorization") String secretKey, KakaoPayDto.approveReq approveReq);

    @PostMapping(value = "/cancel")
    JSONObject cancel(@RequestHeader("Authorization") String secretKey, KakaoPayDto.cancelReq cancelReq);

}

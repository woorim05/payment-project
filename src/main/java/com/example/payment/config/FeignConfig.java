package com.example.payment.config;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@ConditionalOnProperty(name = "globallib.feignclient", havingValue = "on")
@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 20;
    private static final long KEEP_ALIVE_TIME = 60L;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>()
    );

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    ErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    Retryer feignRetryer() {
        // 특정 HTTP 상태코드(예:503 Service Unavaliable), IOException, 사용자 정의(RetryableException) 일 경우 실행됨
        return new FeignRetryer(3, 1500, 4500, 10000, threadPoolExecutor);
    }
}

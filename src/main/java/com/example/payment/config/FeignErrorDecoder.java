package com.example.payment.config;

import com.example.payment.enums.ServiceErrorCode;
import com.example.payment.exception.ServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Map;

@Log4j2
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            Object decode = new GsonDecoder().decode(response, Map.class);
            log.error(decode);

            return new ServiceException(ServiceErrorCode.API_RESPONSE_FAIL, "", decode);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

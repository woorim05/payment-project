package com.example.payment.dto;

import com.example.payment.enums.ServiceErrorCode;
import com.example.payment.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    @Builder.Default
    private Integer status = 200;
    private String code;
    private String message;
    @Builder.Default
    private String timestamp = LocalDateTime.now().toString();
    private T data;

    public static ResponseDto<?> success(Object data) {
        return ResponseDto.builder().code("200").message("SUCCESS").data(data).build();
    }

    public static ResponseDto<?> fail(String msessage) {
        return ResponseDto.builder()
                .status(ServiceErrorCode.SERVER_ERROR.getStatus())
                .code(ServiceErrorCode.SERVER_ERROR.getCode())
                .message(msessage)
                .build();
    }

    public static ResponseDto<?> failService(ServiceException ex, Object data) {
        return ResponseDto.builder()
                .status(ex.getStatus())
                .code(ex.getCode())
                .message(ex.getMessage())
                .data(data)
                .build();
    }
}

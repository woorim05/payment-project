package com.example.payment.exception;

import com.example.payment.enums.EnumErrorType;
import com.example.payment.enums.ServiceErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private int status;
    private String code;
    private String message;
    private Object data;

    public ServiceException(ServiceErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getDescription();
    }

    public ServiceException(EnumErrorType errType, String message) {
        message = StringUtils.hasText(message) ? message : errType.getDescription();

        this.status = errType.getStatus();
        this.code = errType.getCode();
        this.message = message;
    }

    public ServiceException(ServiceErrorCode errType, String message, Object data) {
        message = StringUtils.hasText(message) ? message : errType.getDescription();

        this.status = errType.getStatus();
        this.code = errType.getCode();
        this.message = message;
        this.data = data;
    }
}

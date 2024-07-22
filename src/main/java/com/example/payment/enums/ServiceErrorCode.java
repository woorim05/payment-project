package com.example.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ServiceErrorCode {

    UNAUTHORIZED("00", "접근권한 없음",HttpStatus.UNAUTHORIZED.value()),
    SERVER_ERROR("01", "서버 오류", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    INVALID_IP("02", "접근 불가 IP", HttpStatus.BAD_REQUEST.value()),
    INVALID_HEADER("03", "비 정상 헤더", HttpStatus.BAD_REQUEST.value()),
    INVALID_PARAM("04", "비 정상 파라미터", HttpStatus.BAD_REQUEST.value()),
    API_RESPONSE_FAIL("05", "API 통신 오류", HttpStatus.INTERNAL_SERVER_ERROR.value()),

    FAIL_READY("06", "준비 요청 실패", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    FAIL_APPROVE("07", "승인 요청 실패", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    FAIL_CANCEL("08", "취소 요청 실패", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    ;

    private final String code;
    private final String description;
    private final Integer status;

}

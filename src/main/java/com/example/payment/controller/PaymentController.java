package com.example.payment.controller;

import com.example.payment.dto.OrderDto;
import com.example.payment.dto.PaymentsDto;
import com.example.payment.dto.ResponseDto;
import com.example.payment.exception.ServiceException;
import com.example.payment.service.PaymentsService;
import com.example.payment.service.PaymentsServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentsServiceFactory paymentsServiceFactory;

    @PostMapping(value = "/ready/{payMethod}/{orderId}")
    public ResponseEntity<ResponseDto<?>> ready(@PathVariable String payMethod, @PathVariable String orderId) {
        ResponseDto<?> responseDto;

        try {
            PaymentsService paymentsService = paymentsServiceFactory.getPaymentService(payMethod);
            OrderDto orderInfo = paymentsService.getOrderInfo(orderId);
            orderInfo.setPayMethod(payMethod);
            Map<String, Object> result = paymentsService.ready(orderInfo);

            responseDto = ResponseDto.success(result);

        } catch (ServiceException ex) {
            responseDto = ResponseDto.failService(ex, ex.getData());

        } catch (Exception ex) {
            ex.printStackTrace();
            responseDto = ResponseDto.fail(ex.getMessage());
        }

        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(responseDto.getStatus()));
    }

    @GetMapping(value = "/approve/{payMethod}")
    public ResponseEntity<ResponseDto<?>> approve(@PathVariable String payMethod, @RequestParam Map<String, Object> paramMap) {
        ResponseDto<?> responseDto;

        try {
            PaymentsService paymentsService = paymentsServiceFactory.getPaymentService(payMethod);
            PaymentsDto result = paymentsService.approve(paramMap);

            responseDto = ResponseDto.success(result);

        } catch (ServiceException ex) {
            responseDto = ResponseDto.failService(ex, ex.getData());

        } catch (Exception ex) {
            ex.printStackTrace();
            responseDto = ResponseDto.fail(ex.getMessage());
        }

        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(responseDto.getStatus()));
    }

    @PostMapping(value = "/cancel/{payMethod}/{orderId}")
    public ResponseEntity<ResponseDto<?>> cancel(@PathVariable String payMethod, @PathVariable String orderId) {
        ResponseDto<?> responseDto;

        try {
            PaymentsService paymentsService = paymentsServiceFactory.getPaymentService(payMethod);
            PaymentsDto paymentsDto = paymentsService.getPaymentsInfo(payMethod, orderId);
            Map<String, Object> result = paymentsService.cancel(paymentsDto);

            responseDto = ResponseDto.success(result);

        } catch (ServiceException ex) {
            responseDto = ResponseDto.failService(ex, ex.getData());

        } catch (Exception ex) {
            ex.printStackTrace();
            responseDto = ResponseDto.fail(ex.getMessage());
        }

        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(responseDto.getStatus()));
    }
}

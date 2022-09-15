package com.dodo.bedi.sms.handler;

import com.dodo.bedi.ErrorResponse;
import com.dodo.bedi.sms.exception.SmsSendFailedException;
import com.dodo.bedi.sms.exception.AuthenticationNumberMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SMSExceptionHandler {

    //인증번호 불일치
    @ExceptionHandler(AuthenticationNumberMismatchException.class)
    public ResponseEntity<ErrorResponse> AuthenticationNumberMismatchException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(401, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    //전송실패
    @ExceptionHandler(SmsSendFailedException.class)
    public ResponseEntity<ErrorResponse> SmsSendFailedException(Exception e ) {
        ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}

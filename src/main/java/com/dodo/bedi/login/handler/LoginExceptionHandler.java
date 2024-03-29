package com.dodo.bedi.login.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dodo.bedi.login.exception.DuplicateEmailException;
import com.dodo.bedi.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;


@RestControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> responseResponseEntity() {
        ErrorResponse errorResponse = new ErrorResponse(401, "ID 또는 비밀번호가 일치하지 않습니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // Refresh Token 만료
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> refreshTokenExpiredException() {
        ErrorResponse errorResponse = new ErrorResponse(401, "Refresh Token이 만료되었습니다. 다시 로그인을 진행하여 Token을 갱신해주세요.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // 잘못된 Refresh Token
    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponse> refreshTokenVerificationException() {
        ErrorResponse errorResponse = new ErrorResponse(400, "유효하지 않은 Refresh Token 입니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //이메일 중복
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> DuplicateEmailException(Exception e ) {
        ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //이메일 없음
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> NoSuchElementException() {
        ErrorResponse errorResponse = new ErrorResponse(400, "이메일을 다시 확인 해주세요");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}

package com.deadline826.bedi.Goal.handler;

import com.deadline826.bedi.Goal.exception.*;
import com.deadline826.bedi.exception.ErrorResponse;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GoalExceptionHandler {

    @ExceptionHandler(OutRangeOfGoalException.class)
    public ResponseEntity<ErrorResponse> handleOutRangeOfGoalException() {
        ErrorResponse errorResponse = new ErrorResponse(403, "목표 위치로 부터 너무 멉니다.");
        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(WrongGoalIDException.class)
    public ResponseEntity<ErrorResponse> handleWrongGoalIDException() {
        ErrorResponse errorResponse = new ErrorResponse(404, "잘못된 목표 아이디입니다.");
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PastModifyException.class)
    public ResponseEntity<ErrorResponse> handlePastModifyException() {
        ErrorResponse errorResponse = new ErrorResponse(400, "과거 날짜의 목표는 수정, 삭제, 달성이 불가능합니다.");
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(TooCloseException.class)
    public ResponseEntity<ErrorResponse> handleTooCloseException() {
        ErrorResponse errorResponse = new ErrorResponse(403, "목표 위치로부터 너무 가깝습니다.");
        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(errorResponse);
    }



    @ExceptionHandler(FutureModifyException.class)
    public ResponseEntity<ErrorResponse> handleFutureModifyException() {
        ErrorResponse errorResponse = new ErrorResponse(400, "미래 목표는 달성이 불가능합니다.");
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(errorResponse);
    }

}

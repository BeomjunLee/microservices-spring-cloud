package com.msa.userservice;

import com.msa.userservice.response.Response;
import com.msa.userservice.response.enums.Code;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    /**
     * 아이디 중복 오류
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity duplicateUsername(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.builder()
                .code(Code.FAIL)
                .status(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .build());
    }

}

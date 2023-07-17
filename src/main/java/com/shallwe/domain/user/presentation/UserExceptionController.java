package com.shallwe.domain.user.presentation;


import com.shallwe.domain.user.exception.DefaultException;
import com.shallwe.global.dto.response.ResponseCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionController {
    /**
     * User Exceptions
     */
    @ExceptionHandler(DefaultException.class)
    public ResponseCustom<?> catchUserNotFoundException(DefaultException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }
}
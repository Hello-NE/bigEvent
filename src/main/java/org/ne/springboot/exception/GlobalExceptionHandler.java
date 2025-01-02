package org.ne.springboot.exception;


import org.ne.springboot.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(!e.getMessage().isEmpty() ? e.getMessage() : "操作失败！");
    }
}

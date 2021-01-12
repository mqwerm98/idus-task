package com.idus.demo.advice;

import com.idus.demo.advice.exception.LoginFailedException;
import com.idus.demo.advice.exception.UnauthorizedException;
import com.idus.demo.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Response userNotFoundException(UsernameNotFoundException e) {
        return Response.ERROR("잘못된 유저 정보 입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Response emailLoginFailedException(LoginFailedException e) {
        return Response.ERROR("잘못된 로그인 정보 입니다.", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Response unauthorizedException(UnauthorizedException e) {
        return Response.ERROR("다시 로그인 해주세요.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = String.format("[%s] %s", e.getBindingResult().getFieldError().getField(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return Response.BAD_REQUEST_ERROR(errorMessage);
    }
}

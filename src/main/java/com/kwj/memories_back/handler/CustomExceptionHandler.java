package com.kwj.memories_back.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kwj.memories_back.common.dto.response.ResponseDto;

@RestControllerAdvice // 예외 발생 시 캐치해서 처리할 수 있도록
public class CustomExceptionHandler {

    // description : MethodArgumentNotValidException - 유효성 검사 실패
    // description : HttpMessageNotReadableException - RequestBody가 필요한데 존재하지 않을 때
    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        HttpMessageNotReadableException.class
    })
    public ResponseEntity<ResponseDto> validExceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseDto.validationFail();
    }
    
}

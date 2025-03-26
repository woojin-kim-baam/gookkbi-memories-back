package com.kwj.memories_back.common.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 모든 필드를 초기화하는 생성자를 생성하되, 접근 제한을 private으로 설정
public class ResponseDto {
    private String code;
    private String message;

    protected ResponseDto(){ // protected 패키지 내부 혹은 상속받은 이들만 사용 가능
        this.code = ResponseCode.SUCCESS;
        this.message = ResponseMessage.SUCCESS; // 이 둘은 response에있는 인터페이스에서 가져옴
    }

    public static ResponseEntity<ResponseDto> success(HttpStatus status){
        ResponseDto body = new ResponseDto();
        return ResponseEntity.status(status).body(body);
    }
    public static ResponseEntity<ResponseDto> validationFail(){
        ResponseDto body = new ResponseDto(ResponseCode.VALIDATION_FAIL, ResponseMessage.VALIDATION_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    public static ResponseEntity<ResponseDto> existUser(){
        ResponseDto body = new ResponseDto(ResponseCode.EXIST_USER, ResponseMessage.EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    public static ResponseEntity<ResponseDto> noExistDiary(){
        ResponseDto body = new ResponseDto(ResponseCode.NO_EXIST_DIARY, ResponseMessage.NO_EXIST_DIARY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    public static ResponseEntity<ResponseDto> signInFail(){
        ResponseDto body = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
    public static ResponseEntity<ResponseDto> noPermission(){
        ResponseDto body = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
    public static ResponseEntity<ResponseDto> databaseError(){
        ResponseDto body = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}

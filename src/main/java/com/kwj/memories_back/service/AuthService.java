package com.kwj.memories_back.service;

import org.springframework.http.ResponseEntity;

import com.kwj.memories_back.common.dto.request.auth.IdCheckRequestDto;
import com.kwj.memories_back.common.dto.request.auth.SignInRequestDto;
import com.kwj.memories_back.common.dto.request.auth.SignUpRequestDto;
import com.kwj.memories_back.common.dto.response.ResponseDto;
import com.kwj.memories_back.common.dto.response.auth.SignInResponseDto;

public interface AuthService {
    // 이거 만들자 마자 AuthController 가서 의존성 주입
    // 그리고 implement만들기
    ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto); // super 쓰는 이유 : 선언부만 보고 어떤걸 쓰는지 보기위해(자식 것만), ResponseDto만 넣어서 extend를 한다면 ResponseDto 애들만 오기에 제한의 이유가 사라짐 
}

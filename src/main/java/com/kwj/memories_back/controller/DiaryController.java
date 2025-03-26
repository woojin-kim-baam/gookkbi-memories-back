package com.kwj.memories_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwj.memories_back.common.dto.request.diary.PostDiaryRequestDto;
import com.kwj.memories_back.common.dto.response.ResponseDto;
import com.kwj.memories_back.common.dto.response.diary.GetDiaryResponseDto;
import com.kwj.memories_back.common.dto.response.diary.GetMyDiaryResponseDto;
import com.kwj.memories_back.common.dto.response.diary.PatchDiaryRequestDto;
import com.kwj.memories_back.handler.OAuth2SuccessHandler;
import com.kwj.memories_back.service.DiaryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final OAuth2SuccessHandler OAuth2SuccessHandler;
    
    private final DiaryService diaryService;


    @PostMapping(value = {"", "/"})
    public ResponseEntity<ResponseDto> postDiary(
        @RequestBody @Valid PostDiaryRequestDto requestBody,
        @AuthenticationPrincipal String userId // 인증 접근 주체, JWTAuthenticationFilter에서 가져옴, 저 어노테이션을 쓰면 가져오기 가능, 거기서 String으로 썼기 때문에 String으로 가져온거지 원래는 object 타입임
    ) {
        ResponseEntity<ResponseDto> response = diaryService.postDiary(requestBody, userId);
        return response;
    }

    @GetMapping("/my")
    public ResponseEntity<? super GetMyDiaryResponseDto> getMyDiary(
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super GetMyDiaryResponseDto> response = diaryService.getMyDiary(userId);
        return response;
    }

    @GetMapping("/{diaryNumber}")
    public ResponseEntity<? super GetDiaryResponseDto> getDiary(
        @PathVariable("diaryNumber") Integer diaryNumber
    ){
        ResponseEntity<? super GetDiaryResponseDto> response = diaryService.getDiary(diaryNumber);
        return response;
    }

    @PatchMapping("/{diaryNumber}")
    public ResponseEntity<ResponseDto> patchDiary(
        @RequestBody @Valid PatchDiaryRequestDto requestBody,
        @PathVariable("diaryNumber") Integer diaryNumber,
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<ResponseDto> response = diaryService.patchDiary(requestBody, diaryNumber, userId);
        return response;
    }

    @DeleteMapping("/{diaryNumber}")
    public ResponseEntity<ResponseDto> deleteDiary(
        @PathVariable("diaryNumber") Integer diaryNumber,
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<ResponseDto> response = diaryService.deleteDiary(diaryNumber, userId);
        return response;
    }


}

package com.kwj.memories_back.service;

import org.springframework.http.ResponseEntity;

import com.kwj.memories_back.common.dto.request.diary.PostCommentRequestDto;
import com.kwj.memories_back.common.dto.request.diary.PostDiaryRequestDto;
import com.kwj.memories_back.common.dto.response.ResponseDto;
import com.kwj.memories_back.common.dto.response.diary.GetCommentResponseDto;
import com.kwj.memories_back.common.dto.response.diary.GetDiaryResponseDto;
import com.kwj.memories_back.common.dto.response.diary.GetEmpathyResponseDto;
import com.kwj.memories_back.common.dto.response.diary.GetMyDiaryResponseDto;
import com.kwj.memories_back.common.dto.response.diary.PatchDiaryRequestDto;

public interface DiaryService {
    ResponseEntity<ResponseDto> postDiary(PostDiaryRequestDto dto, String userId);
    ResponseEntity<? super GetMyDiaryResponseDto> getMyDiary(String userId);
    ResponseEntity<? super GetDiaryResponseDto> getDiary(Integer diaryNumber);
    ResponseEntity<ResponseDto> patchDiary(PatchDiaryRequestDto dto, Integer diaryNumber, String userId);
    ResponseEntity<ResponseDto> deleteDiary(Integer diaryNumber, String userId);


    ResponseEntity<? super GetEmpathyResponseDto> getEmpathy(Integer diaryNumber);
    ResponseEntity<ResponseDto> putEmpathy(Integer diaryNumber, String userId);

    ResponseEntity<? super GetCommentResponseDto> getComment(Integer diaryNumber);

    ResponseEntity<ResponseDto> postComment(PostCommentRequestDto dto, Integer diaryNumber, String userId);
}

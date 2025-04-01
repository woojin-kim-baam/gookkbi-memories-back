package com.kwj.memories_back.service;

import org.springframework.http.ResponseEntity;

import com.kwj.memories_back.common.dto.request.openai.GetWayRequestDto;
import com.kwj.memories_back.common.dto.response.openai.GetWayResponseDto;

public interface OpenAiService {
    ResponseEntity<? super GetWayResponseDto> getWay(GetWayRequestDto dto, String userId);
}

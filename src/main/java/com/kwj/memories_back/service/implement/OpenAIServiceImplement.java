package com.kwj.memories_back.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.kwj.memories_back.common.dto.request.openai.ChatRequestDto;
import com.kwj.memories_back.common.dto.response.openai.ChatResponseDto;
import com.kwj.memories_back.common.vo.GptMessageVo;
import com.kwj.memories_back.service.OpenAiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImplement implements OpenAiService {

    private final WebClient webClient; //webClientconfig에서 받은 설정들로 설정하게 해주는? 느낌인듯

    @Override
    public String chat() {
        String content = "삼성 전자 떡상 해?";
        List<GptMessageVo> message = List.of(new GptMessageVo("user", content));

        ChatRequestDto requestBody = new ChatRequestDto("gpt-4o-mini", message);

        ChatResponseDto responseBody = webClient.post()
            .uri("/chat/completions")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(ChatResponseDto.class)
            .block();

            if(responseBody == null || responseBody.getChoices() == null || responseBody.getChoices().isEmpty()){
                return null;
            }

            return responseBody.getChoices().get(0).getMessage().getContent();
    }
    
}

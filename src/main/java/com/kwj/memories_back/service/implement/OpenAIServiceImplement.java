package com.kwj.memories_back.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.kwj.memories_back.common.dto.request.openai.ChatRequestDto;
import com.kwj.memories_back.common.dto.request.openai.GetWayRequestDto;
import com.kwj.memories_back.common.dto.response.ResponseDto;
import com.kwj.memories_back.common.dto.response.openai.ChatResponseDto;
import com.kwj.memories_back.common.dto.response.openai.GetWayResponseDto;
import com.kwj.memories_back.common.entity.UserEntity;
import com.kwj.memories_back.common.vo.GptMessageVo;
import com.kwj.memories_back.repository.UserRepository;
import com.kwj.memories_back.service.OpenAiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImplement implements OpenAiService {

    private final WebClient webClient; //webClientconfig에서 받은 설정들로 설정하게 해주는? 느낌인듯

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetWayResponseDto> getWay(GetWayRequestDto dto, String userId) {

        UserEntity userEntity = null;

        try {
            userEntity = userRepository.findByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        if(userEntity.getGender() == null || userEntity.getAge() == null) {
            return ResponseDto.validationFail();
        }

        String gender = userEntity.getGender().equals("man") ? "남성" : "여성";
        Integer age = userEntity.getAge();
        String type = dto.getType();

        String content = "대한민국 " + age + "세" + gender + "이 " + type + "을 높일 수 있는 방법에 대해 알려주세요. 결과는 마크다운으로 알려주세요";
        

        List<GptMessageVo> message = List.of(new GptMessageVo("user", content));
        ChatRequestDto requestBody = new ChatRequestDto("gpt-4o-mini", message);


        String result = null;

        try {
            ChatResponseDto responseBody = webClient.post()
                .uri("/chat/completions")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(ChatResponseDto.class)
                .block();

                if(responseBody == null || responseBody.getChoices() == null || responseBody.getChoices().isEmpty()){
                    return ResponseDto.openAIError();
                }
                result = responseBody.getChoices().get(0).getMessage().getContent();
                
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseDto.openAIError();
        }

        return GetWayResponseDto.success(result);



    }
    
}

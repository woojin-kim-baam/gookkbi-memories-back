package com.kwj.memories_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwj.memories_back.service.OpenAiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/open-ai")
@RequiredArgsConstructor
public class OpenAiController {
    
    private final OpenAiService openAiService;

    @GetMapping("")
    public String chat(){
        return openAiService.chat();
    }

}

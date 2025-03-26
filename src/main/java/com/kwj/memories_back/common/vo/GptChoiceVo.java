package com.kwj.memories_back.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GptChoiceVo {
    private int index;
    private GptMessageVo message;
    private String finish_reason;
}

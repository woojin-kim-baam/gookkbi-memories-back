package com.kwj.memories_back.common.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor                     // Serializable은 직렬화를 해주는 녀석(네트워크로 데이터를 전송할 수 있게 만들며 버전을 관리도 해줌, 이 클래스가 아이디(@Id) 형태를 뛰게도 해줌)
public class MemoryTestPk implements Serializable {
    @Column(name="user_id")
    private String userId;
    @Column(name="sequence")
    private Integer sequence;
}
// -> 이거 만들고 MemoryTestEntity 들렀다가 다시 Repository로
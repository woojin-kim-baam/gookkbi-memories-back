package com.kwj.memories_back.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kwj.memories_back.common.dto.request.user.PatchUserRequestDto;
import com.kwj.memories_back.common.dto.response.ResponseDto;
import com.kwj.memories_back.common.dto.response.user.GetSignInUserResponseDto;
import com.kwj.memories_back.common.entity.UserEntity;
import com.kwj.memories_back.repository.UserRepository;
import com.kwj.memories_back.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId) {

        UserEntity userEntity = null;

        try{

            userEntity = userRepository.findByUserId(userId);

        }catch(Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<ResponseDto> patchUser(PatchUserRequestDto dto, String userId) {
        try{

            UserEntity userEntity = userRepository.findByUserId(userId);
            userEntity.patch(dto);
            userRepository.save(userEntity);


        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);
    }
    
}

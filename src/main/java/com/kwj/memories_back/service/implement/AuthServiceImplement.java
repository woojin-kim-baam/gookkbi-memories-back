package com.kwj.memories_back.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kwj.memories_back.common.dto.request.auth.IdCheckRequestDto;
import com.kwj.memories_back.common.dto.request.auth.SignInRequestDto;
import com.kwj.memories_back.common.dto.request.auth.SignUpRequestDto;
import com.kwj.memories_back.common.dto.response.ResponseDto;
import com.kwj.memories_back.common.dto.response.auth.SignInResponseDto;
import com.kwj.memories_back.common.entity.UserEntity;
import com.kwj.memories_back.provider.JwtProvider;
import com.kwj.memories_back.repository.UserRepository;
import com.kwj.memories_back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{



    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto) {

        try{

            String userId = dto.getUserId();
            boolean existUser = userRepository.existsByUserId(userId);
            if(existUser) return ResponseDto.existUser();

        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }


        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto) {
        
        try{
            String userId = dto.getUserId();
            boolean existUser = userRepository.existsByUserId(userId);
            if(existUser) return ResponseDto.existUser(); 

            String userPassword = dto.getUserPassword();
            String encodedPassword = passwordEncoder.encode(userPassword);
            dto.setUserPassword(encodedPassword); // 이걸 통해 암호화 패스워드로 갈아끼우기

            UserEntity userEntity = new UserEntity(dto); // 레코드를 만들기 위해 인스턴스 생성, 이걸 위해 userEntity에서 생성자 생성
            userRepository.save(userEntity); // 여기까지 만들고 나면 controller 가서 mapping 만들기

            
        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String accessToken = null;


        try{
            String userId = dto.getUserId();
            UserEntity userEntity = userRepository.findByUserId(userId); // findByUserId 이것을 만들어 올듯 하다 userRepository에서
            if(userEntity == null) return ResponseDto.signInFail();

            String userPassword = dto.getUserPassword(); // 사용자가 입력한 것
            String encodedPassword = userEntity.getUserPassword(); // 저장되어있는 비밀번호
            boolean isMatch = passwordEncoder.matches(userPassword, encodedPassword);
            if(!isMatch) return ResponseDto.signInFail();

            accessToken = jwtProvider.create(userId);
            
        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(accessToken);
    }
    // 여기까지 하고 프론트로 signup-index.tsx 129번째 줄로 가서 하드하게 작성된거 없애기 위해 요청보내는 작업을 위해 apis-index.ts로 감
}

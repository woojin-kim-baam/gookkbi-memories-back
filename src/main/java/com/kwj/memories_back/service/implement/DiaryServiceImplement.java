package com.kwj.memories_back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kwj.memories_back.common.dto.request.diary.PostDiaryRequestDto;
import com.kwj.memories_back.common.dto.response.ResponseDto;
import com.kwj.memories_back.common.dto.response.diary.GetDiaryResponseDto;
import com.kwj.memories_back.common.dto.response.diary.GetMyDiaryResponseDto;
import com.kwj.memories_back.common.dto.response.diary.PatchDiaryRequestDto;
import com.kwj.memories_back.common.entity.DiaryEntity;
import com.kwj.memories_back.repository.DiaryRepository;
import com.kwj.memories_back.service.DiaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryServiceImplement implements DiaryService {

    private final DiaryRepository diaryRepository;

    @Override
    public ResponseEntity<ResponseDto> postDiary(PostDiaryRequestDto dto, String userId) {
        try{
            // filter에서 이미 검증했기에 따로 검증할 필요가 없어서 바로 인스턴스 생성
            DiaryEntity diaryEntity = new DiaryEntity(dto, userId);
            diaryRepository.save(diaryEntity);


        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<? super GetMyDiaryResponseDto> getMyDiary(String userId) {

        List<DiaryEntity> diaryEntities = new ArrayList<>();
        
        try{

            diaryEntities = diaryRepository.findByUserIdOrderByWriteDateDesc(userId);

        }catch(Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetMyDiaryResponseDto.success(diaryEntities);

    }

    @Override
    public ResponseEntity<? super GetDiaryResponseDto> getDiary(Integer diaryNumber) {

        DiaryEntity diaryEntity = null;
        
        try{
            diaryEntity = diaryRepository.findByDiaryNumber(diaryNumber);
            if(diaryEntity == null) return ResponseDto.noExistDiary();


        }catch(Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetDiaryResponseDto.success(diaryEntity);
    }

    @Override
    public ResponseEntity<ResponseDto> patchDiary(PatchDiaryRequestDto dto, Integer diaryNumber, String userId) {
        try{

            DiaryEntity diaryEntity = diaryRepository.findByDiaryNumber(diaryNumber);
            if(diaryEntity == null) return ResponseDto.noExistDiary();
            String writerId = diaryEntity.getUserId();
            boolean isWriter = writerId.equals(userId);
            if(!isWriter) return ResponseDto.noPermission();

            diaryEntity.patch(dto);
            diaryRepository.save(diaryEntity);

            

        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> deleteDiary(Integer diaryNumber, String userId) {
        try{
            DiaryEntity diaryEntity = diaryRepository.findByDiaryNumber(diaryNumber);
            if(diaryEntity == null) return ResponseDto.noExistDiary();
            String writerId = diaryEntity.getUserId();
            boolean isWriter = writerId.equals(userId);
            if(!isWriter) return ResponseDto.noPermission();

            diaryRepository.delete(diaryEntity);
            
        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }
    
}

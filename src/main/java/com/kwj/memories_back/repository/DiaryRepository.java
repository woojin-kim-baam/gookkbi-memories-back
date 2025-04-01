package com.kwj.memories_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kwj.memories_back.common.entity.DiaryEntity;



@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {

    boolean existsByDiaryNumber(Integer diaryNumber);
    
    DiaryEntity findByDiaryNumber(Integer diaryNumber);
    List<DiaryEntity> findByUserIdOrderByWriteDateDesc(String userId);
    List<DiaryEntity> findByOrderByDiaryNumberDesc();


}
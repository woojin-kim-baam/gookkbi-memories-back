package com.kwj.memories_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kwj.memories_back.common.entity.UserEntity;
// UserEntity의 기본키 타입이 String
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsByUserId(String userId); // 이거는 filter 51번째 줄에서 사용하기 위해 후에 작성
    
    UserEntity findByUserId(String userId); // unique한 컬럼으로 조회하기에 한개 혹은 0개의 컬럼만 존재하게 되어있음
    UserEntity findByJoinTypeAndSnsId(String joinType, String snsId);

}

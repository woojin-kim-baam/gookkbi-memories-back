package com.kwj.memories_back.common.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.kwj.memories_back.common.dto.request.diary.PostDiaryRequestDto;
import com.kwj.memories_back.common.dto.response.diary.PatchDiaryRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="diary")
@Table(name="diary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // GenerationType.IDENTITY -> auto increment를 위해
    public Integer diaryNumber;
    public String userId;
    public String writeDate;
    public String weather;
    public String feeling;
    public String title;
    public String content;

    // DiaryServiceImplement에서 옴
    public DiaryEntity(PostDiaryRequestDto dto, String userId){
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.userId = userId;
        this.writeDate = now.format(dateTimeFormatter); 
        this.weather = dto.getWeather();
        this.feeling = dto.getFeeling();
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void patch(PatchDiaryRequestDto dto){
        this.weather = dto.getWeather();
        this.feeling = dto.getFeeling();
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}

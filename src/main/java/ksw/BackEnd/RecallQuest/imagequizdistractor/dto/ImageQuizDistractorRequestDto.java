package ksw.BackEnd.RecallQuest.imagequizdistractor.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageQuizDistractorRequestDto {

    private Long imageQuizId;
    private String imageQuizDistractor; //퀴즈 보기 내용
    private boolean validation;
}

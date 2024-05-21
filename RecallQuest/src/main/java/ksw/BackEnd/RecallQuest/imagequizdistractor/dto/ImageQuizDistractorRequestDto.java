package ksw.BackEnd.RecallQuest.imagequizdistractor.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageQuizDistractorRequestDto {

    private Long imageQuizId; //이래도 되는 걸까..?
    private String imageQuizDistractor; //퀴즈 보기 내용
    private boolean validation;
}

package ksw.BackEnd.RecallQuest.imagequizdistractor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestDto {

    private Long imageQuizDistractorId; //이래도 되는 걸까..?
    private String imageQuizDistractor; //퀴즈 보기 내용

    private String pastDistractor; //기존 검색용 보기
    private String revisedDistractor; //갱신할 내용의 보기
    private boolean validation;
}

package ksw.BackEnd.RecallQuest.imagequizdistractor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestDto {

    // 시퀀스 번호로 수정할 경우
//    private Long imageQuizDistractorId;
//    private String imageQuizDistractor; //퀴즈 보기 내용

    private String pastDistractor; //기존 검색용 보기
    private String revisedDistractor; //갱신할 내용의 보기
    private boolean validation;
}

package ksw.BackEnd.RecallQuest.imagequiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestDto {

//    private Long imageQuizId; //퀴즈 아이디
//    private String question;

    private String pastQuestion; //기존 검색용 질문
    private String hint;
    private String revisedQuestion; //갱신할 내용의 질문
}

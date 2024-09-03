package ksw.BackEnd.RecallQuest.Textquiz.dto;

import ksw.BackEnd.RecallQuest.entity.Member;
import lombok.*;



@Setter
@Getter
@Builder
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 생성자를 공개(public)로 설정합니다
public class TextQuizRequestDto {

    private String userLoginId; //회원 아이디

//    private Member member;

    private Long memberSeq; // Member의 seq를 위한 필드 추가

    private String question;

    private String hint;

}

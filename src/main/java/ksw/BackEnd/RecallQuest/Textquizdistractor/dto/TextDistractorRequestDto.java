package ksw.BackEnd.RecallQuest.Textquizdistractor.dto;

import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 생성자를 공개(public)로 설정합니다
public class TextDistractorRequestDto {

    private Long textQuizId;
    private String textzQuizDistractor;
    private boolean validation;
}

// choiceText
// answer
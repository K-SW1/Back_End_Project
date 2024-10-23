package ksw.BackEnd.RecallQuest.ImagequizDistractor.dto;

import lombok.*;
@Setter
@Getter
@Builder
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 생성자를 공개(public)로 설정합니다
public class ImageDistractorRequestDto {

    private Long imageQuizId; // 이미지 퀴즈의 고유 ID
    private String imageQuizDistractor; // 선택지 텍스트
    private boolean validation; // 정답 여부
}

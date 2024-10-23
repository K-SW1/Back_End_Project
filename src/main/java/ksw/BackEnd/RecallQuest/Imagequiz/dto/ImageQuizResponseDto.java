package ksw.BackEnd.RecallQuest.Imagequiz.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ksw.BackEnd.RecallQuest.entity.Member;
import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 생성자를 공개(public)로 설정합니다
public class ImageQuizResponseDto {

    private long imageQuizId; // 이미지 퀴즈의 고유 ID

//    @JsonIgnore
//    private Member member; // 작성자 정보를 숨길 수 있습니다

    private Long memberSeq; // Member의 seq를 위한 필드 추가

    private String question; // 이미지 퀴즈 질문

    private String hint; // 힌트

    private String imageUrl; // 이미지 URL 필드 추가
}

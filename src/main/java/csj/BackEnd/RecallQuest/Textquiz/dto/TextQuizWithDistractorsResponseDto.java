package csj.BackEnd.RecallQuest.Textquiz.dto;

import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorResponseDto;
import csj.BackEnd.RecallQuest.entity.Member;
import lombok.*;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 생성자를 공개(public)로 설정합니다
public class TextQuizWithDistractorsResponseDto {

    private int textQuizId;

    private Member member;
    
    private String question;

    private String hint;

    private List<TextDistractorResponseDto> Distractors; // choices inside (choiceText, answer)

}

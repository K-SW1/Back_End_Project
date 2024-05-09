package csj.BackEnd.RecallQuest.Textquiz.dto;

import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TextQuizWithDistractorsResponseDto {

    private int textQuizId;

    private int memberInfoId;

    private String question;

    private String hint;

    private List<TextDistractorResponseDto> Distractors; // choices inside (choiceText, answer)

}

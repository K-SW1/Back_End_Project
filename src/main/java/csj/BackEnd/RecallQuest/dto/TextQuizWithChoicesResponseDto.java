package csj.BackEnd.RecallQuest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TextQuizWithChoicesResponseDto {

    private int textQuizId;

    private int memberInfoId;

    private String question;

    private String hint;

    private List<TextChoiceResponseDto> choices; // choices inside (choiceText, answer)

}

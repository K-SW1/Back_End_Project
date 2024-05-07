package csj.BackEnd.RecallQuest.Textquiz.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TextQuizResponseDto {

    private int textQuizId;

    private int memberInfoId;

    private String question;

    private String hint;

}

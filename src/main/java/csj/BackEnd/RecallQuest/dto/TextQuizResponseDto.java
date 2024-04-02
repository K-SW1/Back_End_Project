package csj.BackEnd.RecallQuest.dto;

import csj.BackEnd.RecallQuest.domain.TextQuiz;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class TextQuizResponseDto {

    private int textQuizId;

    private int memberInfoId;

    private String question;

    private String hint;

}

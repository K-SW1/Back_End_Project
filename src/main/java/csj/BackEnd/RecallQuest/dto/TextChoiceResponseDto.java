package csj.BackEnd.RecallQuest.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class TextChoiceResponseDto {

    private String choiceText;
    private boolean answer;

}

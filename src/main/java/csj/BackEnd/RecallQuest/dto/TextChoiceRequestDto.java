package csj.BackEnd.RecallQuest.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TextChoiceRequestDto {

    private String choiceText;
    private boolean answer;
}

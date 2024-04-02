package csj.BackEnd.RecallQuest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class TextQuizRequestDto {

    private int memberInfoId;

    private String question;

    private String hint;

}

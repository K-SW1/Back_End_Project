package ksw.BackEnd.RecallQuest.imagequiz.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImageQuizRequestDto {

    private String userLoginId; //회원 아이디
    private String question;
    private String hint;

}

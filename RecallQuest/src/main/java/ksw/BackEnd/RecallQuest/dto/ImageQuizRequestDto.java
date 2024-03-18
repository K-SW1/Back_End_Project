package ksw.BackEnd.RecallQuest.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImageQuizRequestDto {

    private String question;

    private String hint;

    private List<MultipartFile> images = new ArrayList<>();

}

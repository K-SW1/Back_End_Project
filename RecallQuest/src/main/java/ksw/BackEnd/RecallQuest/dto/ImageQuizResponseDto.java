package ksw.BackEnd.RecallQuest.dto;

import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
import ksw.BackEnd.RecallQuest.domain.QuestionImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImageQuizResponseDto {

    private String question;

    private String hint;

    private List<QuestionImage> questionImages;

    @Builder
    public ImageQuizResponseDto(String question, String hint, List<QuestionImage> questionImages) {
        this.question = question;
        this.hint = hint;
        this.questionImages = questionImages;
    }

    //entity에서 Dto로
    public static ImageQuizResponseDto buildDto (ImageQuiz imageQuiz) {
        return ImageQuizResponseDto.builder()
                .question(imageQuiz.getQuestion())
                .hint(imageQuiz.getHint())
                .questionImages(imageQuiz.getQuestionImages())
                .build();
    }
}

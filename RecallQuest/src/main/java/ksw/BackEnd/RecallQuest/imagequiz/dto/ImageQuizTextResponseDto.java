package ksw.BackEnd.RecallQuest.imagequiz.dto;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.QuestionImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImageQuizTextResponseDto {

    private String question;

    private String hint;

    private List<QuestionImage> questionImages;

    @Builder
    public ImageQuizTextResponseDto(String question, String hint, List<QuestionImage> questionImages) {
        this.question = question;
        this.hint = hint;
        this.questionImages = questionImages;
    }

    //entity에서 Dto로
    public static ImageQuizTextResponseDto buildDto (ImageQuiz imageQuiz) {
        return ImageQuizTextResponseDto.builder()
                .question(imageQuiz.getQuestion())
                .hint(imageQuiz.getHint())
                .questionImages(imageQuiz.getQuestionImages())
                .build();
    }
}

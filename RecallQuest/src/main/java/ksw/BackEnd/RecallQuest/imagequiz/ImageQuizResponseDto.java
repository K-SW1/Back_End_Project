package ksw.BackEnd.RecallQuest.imagequiz;

import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
import ksw.BackEnd.RecallQuest.domain.Member;
import ksw.BackEnd.RecallQuest.domain.QuestionImage;
import ksw.BackEnd.RecallQuest.dto.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ImageQuizResponseDto {

    private String question;
    private String hint;
    private List<QuestionImageDto> questionImages = new ArrayList<>();

    public ImageQuizResponseDto(ImageQuiz imageQuiz) {
        this.question = imageQuiz.getQuestion();
        this.hint = imageQuiz.getHint();
        this.questionImages = imageQuiz.getQuestionImages().stream()
                .map(questionImages -> new QuestionImageDto(questionImages))
                .collect(Collectors.toList());
    }

//    //entity에서 Dto로
//    public static ImageQuizResponseDto buildDto (ImageQuiz imageQuiz) {
//        return ImageQuizResponseDto.builder()
//                .question(imageQuiz.getQuestion())
//                .hint(imageQuiz.getHint())
//                .questionImages(imageQuiz.getQuestionImages())
//                .build();
//    }
    public static List<ImageQuizResponseDto> buildImageQuizToList (List<ImageQuiz> imageQuiz) {
        return imageQuiz.stream().map(imageQuizs -> new ImageQuizResponseDto(imageQuizs))
                .collect(Collectors.toList());
    }


    @Getter
    public static class QuestionImageDto {
        private String originFilename; //원본 이름
        private String storeFilename; //파일을 저장한 이름, 원본 이름에서 중복이 날 수 있기 때문에 생성
        private String type; //타입
        private String filePath; //경로

        public QuestionImageDto(QuestionImage questionImage) {
            this.originFilename = questionImage.getOriginFilename();
            this.storeFilename = questionImage.getStoreFilename();
            this.type = questionImage.getType();
            this.filePath = questionImage.getFilePath();
        }
    }

}

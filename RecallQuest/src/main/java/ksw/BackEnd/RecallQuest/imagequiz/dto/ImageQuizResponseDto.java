package ksw.BackEnd.RecallQuest.imagequiz.dto;

import ksw.BackEnd.RecallQuest.entity.*;
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
    private Long imageQuizId;
    private List<QuestionImageDto> questionImages = new ArrayList<>();
    private List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtos = new ArrayList<>();

    public ImageQuizResponseDto(ImageQuiz imageQuiz) {
        this.question = imageQuiz.getQuestion();
        this.hint = imageQuiz.getHint();
        this.imageQuizId = imageQuiz.getId();
        this.questionImages = imageQuiz.getQuestionImages().stream()
                .map(questionImages -> new QuestionImageDto(questionImages))
                .collect(Collectors.toList());
        this.imageQuizDistractorResponseDtos = imageQuiz.getImageQuizDistractors().stream()
                .map(imageQuizDistractorResponseDtos -> new ImageQuizDistractorResponseDto(imageQuizDistractorResponseDtos))
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

    @Getter
    public static class ImageQuizDistractorResponseDto {

        private String imageQuizDistractor;
        private boolean validation;
        private List<ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto.DistractorImageDto> distractorImages = new ArrayList<>();

        public ImageQuizDistractorResponseDto(ImageQuizDistractor imageQuizDistractor) {
            this.imageQuizDistractor = imageQuizDistractor.getImageQuizDistractor();
            this.validation = imageQuizDistractor.isValidation();
            this.distractorImages = imageQuizDistractor.getDistractorImages().stream()
                    .map( distractorImages -> new ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto.DistractorImageDto(distractorImages))
                    .collect(Collectors.toList());
        }

        public static List<ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto> buildImageQuizDistractorToList (List<ImageQuizDistractor> imageQuizDistractor) {
            return imageQuizDistractor.stream().map(imageQuizDistractors -> new ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto(imageQuizDistractors))
                    .collect(Collectors.toList());
        }

        @Getter
        public static class DistractorImageDto {
            private String originFilename; //원본 이름
            private String storeFilename; //파일을 저장한 이름, 원본 이름에서 중복이 날 수 있기 때문에 생성
            private String type; //타입
            private String filePath; //경로

            public DistractorImageDto (DistractorImage distractorImage) {
                this.originFilename = distractorImage.getOriginFilename();
                this.storeFilename = distractorImage.getStoreFilename();
                this.type = distractorImage.getType();
                this.filePath = distractorImage.getFilePath();
            }
        }
    }

}

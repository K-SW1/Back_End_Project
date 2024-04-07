//package ksw.BackEnd.RecallQuest.imagequiz;
//
//import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
//import ksw.BackEnd.RecallQuest.domain.ImageQuizDistractor;
//import ksw.BackEnd.RecallQuest.domain.QuestionImage;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class ImageQuizAndDistractorResponseDto {
//
//    private String question;
//    private String hint;
//    private Long imageQuizId;
//    private List<QuestionImageDto> questionImages = new ArrayList<>();
//    private List<ImageQuizDistractorResponseDto> imageQuizDistractors = new ArrayList<>();
//
//    public ImageQuizAndDistractorResponseDto(ImageQuiz imageQuiz, List<ImageQuizDistractor> imageQuizDistractors) {
//        this.question = imageQuiz.getQuestion();
//        this.hint = imageQuiz.getHint();
//        this.imageQuizId = imageQuiz.getId();
//        this.imageQuizDistractors = imageQuizDistractors;
//        this.questionImages = imageQuiz.getQuestionImages().stream()
//                .map(questionImages -> new QuestionImageDto(questionImages))
//                .collect(Collectors.toList());
//    }
//
////    //entity에서 Dto로
////    public static ImageQuizResponseDto buildDto (ImageQuiz imageQuiz) {
////        return ImageQuizResponseDto.builder()
////                .question(imageQuiz.getQuestion())
////                .hint(imageQuiz.getHint())
////                .questionImages(imageQuiz.getQuestionImages())
////                .build();
////    }
//    public static List<ImageQuizAndDistractorResponseDto> buildImageQuizToList (List<ImageQuiz> imageQuiz) {
//        return imageQuiz.stream().map(imageQuizs -> new ImageQuizAndDistractorResponseDto(imageQuizs))
//                .collect(Collectors.toList());
//    }
//
//
//    @Getter
//    public static class QuestionImageDto {
//        private String originFilename; //원본 이름
//        private String storeFilename; //파일을 저장한 이름, 원본 이름에서 중복이 날 수 있기 때문에 생성
//        private String type; //타입
//        private String filePath; //경로
//
//        public QuestionImageDto(QuestionImage questionImage) {
//            this.originFilename = questionImage.getOriginFilename();
//            this.storeFilename = questionImage.getStoreFilename();
//            this.type = questionImage.getType();
//            this.filePath = questionImage.getFilePath();
//        }
//    }
//
//}

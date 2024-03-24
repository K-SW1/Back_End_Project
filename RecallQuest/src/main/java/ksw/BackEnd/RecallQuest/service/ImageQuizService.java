//package ksw.BackEnd.RecallQuest.service;
//
//import ksw.BackEnd.RecallQuest.common.FileStore;
//import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
//import ksw.BackEnd.RecallQuest.domain.QuestionImage;
//import ksw.BackEnd.RecallQuest.imagequiz.ImageQuizRequestDto;
//import ksw.BackEnd.RecallQuest.repository.ImageQuizRepository;
//import ksw.BackEnd.RecallQuest.repository.QuestionImageRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.zip.Deflater;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ImageQuizService {
//
//    private final QuestionImageRepository questionImageRepository;
//    private final ImageQuizRepository imageQuizRepository;
//    private final FileStore fileStore;
//
//    public List<QuestionImage> saveQuestionImages(List<MultipartFile> imageFiles) throws IOException {
//        List<QuestionImage> questionImages = fileStore.storeFiles(imageFiles);
//        return questionImages;
//    }
//
//    public List<QuestionImage> findQuestionImages() {
//        List<QuestionImage> questionImages = questionImageRepository.findAll();
//
//        return questionImages;
//    }
//
//    public ImageQuiz saveImageQuizQuestion(ImageQuizRequestDto imageQuizRequestDto) throws IOException {
//
//        // 이미지 퀴즈를 저장하기 위해 요청 DTO로부터 필요한 정보를 추출하여 처리
//        List<QuestionImage> questionImages = saveQuestionImages(imageQuizRequestDto.getImages());
//
//        // 이미지 퀴즈 엔티티 생성
//        ImageQuiz imageQuiz = ImageQuiz.builder()
//                .question(imageQuizRequestDto.getQuestion())
//                .hint(imageQuizRequestDto.getHint())
//                .questionImages(questionImages)
//                .build();
//
//        // 이미지 퀴즈 엔티티 저장
//        return imageQuizRepository.save(imageQuiz);
//    }
//
//    public ImageQuiz post(ImageQuizRequestDto imageQuizRequestDto) throws IOException {
//        List<QuestionImage> questionImages = saveQuestionImages(imageQuizRequestDto.getImages());
//        for (QuestionImage questionImage : questionImages) {
//            log.info(questionImage.getOriginFilename());
//        }
//
//        // 이미지 퀴즈 엔티티 생성
//        ImageQuiz imageQuiz = ImageQuiz.builder()
//                .question(imageQuizRequestDto.getQuestion())
//                .hint(imageQuizRequestDto.getHint())
//                .questionImages(questionImages)
//                .build();
//
////        questionImages.stream()
////                .forEach(questionImage -> imageQuiz.setQuestionImage(questionImage));
//
//
//        return imageQuizRepository.save(imageQuiz);
//    }
//}

package ksw.BackEnd.RecallQuest.imagequiz.mapper;

import ksw.BackEnd.RecallQuest.entity.DistractorImage;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.entity.QuestionImage;
import ksw.BackEnd.RecallQuest.imagequiz.dto.CompleteImageQuizResponseDto;
import ksw.BackEnd.RecallQuest.imagequiz.dto.ImageQuizResponseDto;
import ksw.BackEnd.RecallQuest.imagequiz.service.ImageQuizService;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.service.ImageQuizDistractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class QuizMapper {

    private final ImageQuizService imageQuizService;

    public List<Map<String, Object>> quizPhotoMapping(List<QuestionImage> questionImages) throws IOException {

        List<Map<String, Object>> imageList = new ArrayList<>();

        for (QuestionImage questionImage : questionImages) {

            byte[] imageData = imageQuizService.downloadImageFromFileSystem(questionImage.getStoreFilename());

            Map<String, Object> imageInfo = new HashMap<>();
            imageInfo.put("fileName", questionImage.getOriginFilename());
            imageInfo.put("imageBytes", imageData);

            imageList.add(imageInfo);
        }
        return imageList;

    }

    public List<ImageQuizResponseDto> toResponse(List<ImageQuiz> ImageQuizList) throws IOException {
        List<ImageQuizResponseDto> imageQuizResponseDtoList = new ArrayList<>();

        for (ImageQuiz imageQuiz : ImageQuizList) {
            List<QuestionImage> questionImages = imageQuiz.getQuestionImages();
            List<Map<String, Object>> imageList = quizPhotoMapping(questionImages);
            ImageQuizResponseDto imageQuizResponseDto = ImageQuizResponseDto.buildPhotoDto(imageQuiz,imageList);
            imageQuizResponseDtoList.add(imageQuizResponseDto);
        }

        return imageQuizResponseDtoList;
    }

}

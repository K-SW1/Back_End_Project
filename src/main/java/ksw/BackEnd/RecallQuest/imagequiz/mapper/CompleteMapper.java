package ksw.BackEnd.RecallQuest.imagequiz.mapper;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.imagequiz.dto.CompleteImageQuizResponseDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.mapper.DistractorMapper;
import ksw.BackEnd.RecallQuest.imagequizdistractor.service.ImageQuizDistractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CompleteMapper {

    private final QuizMapper quizMapper;
    private final DistractorMapper distractorMapper;

    public CompleteImageQuizResponseDto toCompleteResponse(ImageQuiz imageQuiz, List<ImageQuizDistractor> imageQuizDistractors) throws IOException {

        List<Map<String, Object>> imageList = quizMapper.quizPhotoMapping(imageQuiz.getQuestionImages());
        List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtoList = distractorMapper.toResponse(imageQuizDistractors);
        CompleteImageQuizResponseDto completeImageQuizResponseDto = CompleteImageQuizResponseDto.buildPhotoDto(imageQuiz, imageList, imageQuizDistractorResponseDtoList);
        return completeImageQuizResponseDto;
    }

    public List<CompleteImageQuizResponseDto> toCompleteListResponse(List<ImageQuiz> imageQuizList) throws IOException {
        List<CompleteImageQuizResponseDto> result = new ArrayList<>();

//        for (ImageQuiz imageQuiz : imageQuizList) {
//            List<Map<String, Object>> imageList = quizMapper.quizPhotoMapping(imageQuiz.getQuestionImages());
//
//            List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtoList = distractorMapper.toResponse(imageQuizDistractors);
//            CompleteImageQuizResponseDto completeImageQuizResponseDto = CompleteImageQuizResponseDto.buildPhotoDto(imageQuiz, imageList, imageQuizDistractorResponseDtoList);
//            result.add(completeImageQuizResponseDto);
//        }

        return result;
    }


}

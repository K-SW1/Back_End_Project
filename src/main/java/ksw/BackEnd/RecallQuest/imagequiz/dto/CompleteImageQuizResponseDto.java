package ksw.BackEnd.RecallQuest.imagequiz.dto;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CompleteImageQuizResponseDto {

    private String question;
    private String hint;
    private Long imageQuizId;
    private Long imageQuizSeq;
    private List<Map<String, Object>> imageList;
    private List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtos = new ArrayList<>();

    public CompleteImageQuizResponseDto(ImageQuiz imageQuiz, List<Map<String, Object>> imageList, List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtos) {
        this.question = imageQuiz.getQuestion();
        this.hint = imageQuiz.getHint();
        this.imageQuizId = imageQuiz.getImageQuizSeq();
        this.imageList = imageList;
        this.imageQuizDistractorResponseDtos = imageQuizDistractorResponseDtos;
    }

    public static CompleteImageQuizResponseDto buildPhotoDto (ImageQuiz imageQuiz, List<Map<String, Object>> imageList, List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtos) {
        CompleteImageQuizResponseDto completeImageQuizResponseDto = new CompleteImageQuizResponseDto();
        completeImageQuizResponseDto.setQuestion(imageQuiz.getQuestion());
        completeImageQuizResponseDto.setHint(imageQuiz.getHint());
        completeImageQuizResponseDto.setImageQuizId(imageQuiz.getImageQuizSeq());
        completeImageQuizResponseDto.setImageList(imageList);
        completeImageQuizResponseDto.setImageQuizDistractorResponseDtos(imageQuizDistractorResponseDtos);
        return completeImageQuizResponseDto;
    }

    public static List<CompleteImageQuizResponseDto> buildCompleteImageQuizDistractorToList (List<ImageQuiz> imageQuizList, List<Map<String, Object>> imageList, List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtos) {
        List<CompleteImageQuizResponseDto> result = new ArrayList<>();

        for (ImageQuiz imageQuiz : imageQuizList) {
            CompleteImageQuizResponseDto dto = new CompleteImageQuizResponseDto(imageQuiz, imageList, imageQuizDistractorResponseDtos);
            result.add(dto);
        }
        return result;
    }

}

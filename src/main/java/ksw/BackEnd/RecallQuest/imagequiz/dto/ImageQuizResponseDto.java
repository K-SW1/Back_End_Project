package ksw.BackEnd.RecallQuest.imagequiz.dto;

import ksw.BackEnd.RecallQuest.entity.*;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto;
import lombok.Builder;
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
public class ImageQuizResponseDto {

    private String question;
    private String hint;
    private Long imageQuizId;
    private List<Map<String, Object>> imageList;
//    private List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtos = new ArrayList<>();

    public ImageQuizResponseDto(ImageQuiz imageQuiz, List<Map<String, Object>> imageList) {
        this.question = imageQuiz.getQuestion();
        this.hint = imageQuiz.getHint();
        this.imageQuizId = imageQuiz.getImageQuizSeq();
        this.imageList = imageList;
//        this.imageQuizDistractorResponseDtos = imageQuiz.getImageQuizDistractors().stream()
//                .map(imageQuizDistractorResponseDtos -> new ImageQuizDistractorResponseDto(imageQuizDistractorResponseDtos))
//                .collect(Collectors.toList());
    }

    public static ImageQuizResponseDto buildPhotoDto (ImageQuiz imageQuiz, List<Map<String, Object>> imageList) {
        ImageQuizResponseDto imageQuizResponseDto = new ImageQuizResponseDto();
        imageQuizResponseDto.setQuestion(imageQuiz.getQuestion());
        imageQuizResponseDto.setHint(imageQuiz.getHint());
        imageQuizResponseDto.setImageQuizId(imageQuiz.getImageQuizSeq());
        imageQuizResponseDto.setImageList(imageList);
        return imageQuizResponseDto;
    }

    public static List<ImageQuizDistractorResponseDto> buildImageQuizDistractorToList (List<ImageQuizDistractor> imageQuizDistractor, List<Map<String, Object>> imageList) {
        return imageQuizDistractor.stream().map(imageQuizDistractors -> new ImageQuizDistractorResponseDto(imageQuizDistractors, imageList))
                .collect(Collectors.toList());
    }

}

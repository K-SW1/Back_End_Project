package ksw.BackEnd.RecallQuest.imagequizdistractor.dto;

import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ImageQuizDistractorResponseDto {

    private String imageQuizDistractor;
    private boolean validation;
    private List<Map<String, Object>> imageList;

    public ImageQuizDistractorResponseDto(ImageQuizDistractor imageQuizDistractor, List<Map<String, Object>> imageList) {
        this.imageQuizDistractor = imageQuizDistractor.getImageQuizDistractor();
        this.validation = imageQuizDistractor.isValidation();
        this.imageList = imageList;
    }

    public static ImageQuizDistractorResponseDto buildPhotoDto (ImageQuizDistractor imageQuizDistractor, List<Map<String, Object>> imageList) {
        ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = new ImageQuizDistractorResponseDto();
        imageQuizDistractorResponseDto.setImageQuizDistractor(imageQuizDistractor.getImageQuizDistractor());
        imageQuizDistractorResponseDto.setValidation(imageQuizDistractor.isValidation());
        imageQuizDistractorResponseDto.setImageList(imageList);
        return imageQuizDistractorResponseDto;
    }

    public static List<ImageQuizDistractorResponseDto> buildImageQuizDistractorToList (List<ImageQuizDistractor> imageQuizDistractor, List<Map<String, Object>> imageList) {
        return imageQuizDistractor.stream().map(imageQuizDistractors -> new ImageQuizDistractorResponseDto(imageQuizDistractors, imageList))
                .collect(Collectors.toList());
    }

}

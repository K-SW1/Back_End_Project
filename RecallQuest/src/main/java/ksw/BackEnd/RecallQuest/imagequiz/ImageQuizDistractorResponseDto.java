package ksw.BackEnd.RecallQuest.imagequiz;

import ksw.BackEnd.RecallQuest.domain.DistractorImage;
import ksw.BackEnd.RecallQuest.domain.ImageQuizDistractor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ImageQuizDistractorResponseDto {

    private String imageQuizDistractor;
    private boolean validation;
    private List<DistractorImageDto> distractorImages = new ArrayList<>();

    public ImageQuizDistractorResponseDto(ImageQuizDistractor imageQuizDistractor) {
        this.imageQuizDistractor = imageQuizDistractor.getImageQuizDistractor();
        this.validation = imageQuizDistractor.isValidation();
        this.distractorImages = imageQuizDistractor.getDistractorImages().stream()
                .map( distractorImages -> new DistractorImageDto(distractorImages))
                .collect(Collectors.toList());
    }

    public static List<ImageQuizDistractorResponseDto> buildImageQuizDistractorToList (List<ImageQuizDistractor> imageQuizDistractor) {
        return imageQuizDistractor.stream().map(imageQuizDistractors -> new ImageQuizDistractorResponseDto(imageQuizDistractors))
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

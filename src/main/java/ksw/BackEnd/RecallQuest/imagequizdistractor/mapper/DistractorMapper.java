package ksw.BackEnd.RecallQuest.imagequizdistractor.mapper;

import ksw.BackEnd.RecallQuest.entity.DistractorImage;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorResponseDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.service.ImageQuizDistractorService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DistractorMapper {

    private final ImageQuizDistractorService imageQuizDistractorService;

    public List<Map<String, Object>> distractorPhotoMapping(List<DistractorImage> distractorImages) throws IOException {

        List<Map<String, Object>> imageList = new ArrayList<>();

        for (DistractorImage distractorImage : distractorImages) {

            byte[] imageData = imageQuizDistractorService.downloadImageFromFileSystem(distractorImage.getStoreFilename());

            Map<String, Object> imageInfo = new HashMap<>();
            imageInfo.put("fileName", distractorImage.getOriginFilename());
            imageInfo.put("imageBytes", imageData);

            imageList.add(imageInfo);
        }
        return imageList;
    }

    public List<ImageQuizDistractorResponseDto> toResponse(List<ImageQuizDistractor> imageQuizDistractors) throws IOException {
        List<ImageQuizDistractorResponseDto> imageQuizDistractorResponseDtoList = new ArrayList<>();

        for (ImageQuizDistractor imageQuizDistractor : imageQuizDistractors) {
            List<DistractorImage> distractorImages = imageQuizDistractor.getDistractorImages();
            List<Map<String, Object>> imageList = distractorPhotoMapping(distractorImages);
            ImageQuizDistractorResponseDto imageQuizDistractorResponseDto = ImageQuizDistractorResponseDto.buildPhotoDto(imageQuizDistractor,imageList);
            imageQuizDistractorResponseDtoList.add(imageQuizDistractorResponseDto);
        }

        return imageQuizDistractorResponseDtoList;
    }


}

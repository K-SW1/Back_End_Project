package ksw.BackEnd.RecallQuest.ImagequizDistractor.mapper;

import ksw.BackEnd.RecallQuest.ImagequizDistractor.dto.ImageDistractorRequestDto;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.dto.ImageDistractorResponseDto;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.dao.JpaImageDistractorDao;
import ksw.BackEnd.RecallQuest.entity.ImagequizDistractor;
import ksw.BackEnd.RecallQuest.Imagequiz.dto.ImageQuizWithDistractorsResponseDto;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImageDistractorMapper {

    private final JpaImageDistractorDao jpaImageDistractorDao;

    /**
     * 응답 매퍼
     */
    public ImageDistractorResponseDto toResponse(ImagequizDistractor imageDistractor) {
        return ImageDistractorResponseDto.builder()
                .imageQuizDistractor(imageDistractor.getImageDistractor())
                .validation(imageDistractor.isValidation())
                .build();
    }

    public ImageQuizWithDistractorsResponseDto toResponseDto(ImageQuiz imageQuiz) {
        List<ImagequizDistractor> imageDistractors = jpaImageDistractorDao.findByImageQuiz_ImageQuizId(imageQuiz.getImageQuizId());
        List<ImageDistractorResponseDto> distractorResponseDtos = imageDistractors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ImageQuizWithDistractorsResponseDto.builder()
                .imageQuizId(imageQuiz.getImageQuizId())
                .question(imageQuiz.getQuestion())
                .hint(imageQuiz.getHint())
                .distractors(distractorResponseDtos)
                .build();
    }

    public List<ImageDistractorResponseDto> toResponseDistractorList(List<ImagequizDistractor> imageDistractors) {
        return imageDistractors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ImageQuizWithDistractorsResponseDto> toResponseImageQuizList(List<ImageQuiz> imageQuizzes) {
        return imageQuizzes.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 요청 매퍼
     */
    public ImageDistractorRequestDto toRequestDistractor(ImagequizDistractor imageDistractor) {
        return ImageDistractorRequestDto.builder()
                .imageQuizDistractor(imageDistractor.getImageDistractor())
                .validation(imageDistractor.isValidation())
                .build();
    }

    public List<ImageDistractorRequestDto> toRequestDistractorList(List<ImagequizDistractor> imageDistractors) {
        return imageDistractors.stream()
                .map(this::toRequestDistractor)
                .collect(Collectors.toList());
    }
}

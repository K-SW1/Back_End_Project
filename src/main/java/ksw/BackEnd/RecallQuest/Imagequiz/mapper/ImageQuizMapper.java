package ksw.BackEnd.RecallQuest.Imagequiz.mapper;

import ksw.BackEnd.RecallQuest.Imagequiz.dto.ImageQuizWithDistractorsResponseDto;
import ksw.BackEnd.RecallQuest.ImagequizDistractor.dto.ImageDistractorResponseDto;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.ImagequizDistractor;
import ksw.BackEnd.RecallQuest.Imagequiz.dto.ImageQuizResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImageQuizMapper {

    /**
     * 응답 매퍼
     */
    public ImageQuizResponseDto toDto(ImageQuiz entity) {
        return ImageQuizResponseDto.builder()
                .imageQuizId(entity.getImageQuizId()) // 이미지 퀴즈 ID
                .memberSeq(entity.getMember().getMemberSeq()) // memberSeq 설정
                .question(entity.getQuestion())
                .hint(entity.getHint())
                .imageUrl(entity.getImageUrl()) // 이미지 URL 설정
                .build();
    }

    public List<ImageQuizResponseDto> toDtoList(List<ImageQuiz> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ImageQuizWithDistractorsResponseDto toResponseDto(ImageQuiz entity) {
        return ImageQuizWithDistractorsResponseDto.builder()
                .imageQuizId(entity.getImageQuizId()) // 이미지 퀴즈 ID 설정
                .memberSeq(entity.getMember().getMemberSeq()) // 작성자 정보 설정
                .question(entity.getQuestion()) // 질문 설정
                .hint(entity.getHint()) // 힌트 설정
                .imageUrl(entity.getImageUrl()) // 이미지 URL 설정
                .distractors(toDistractorResponseDtoList(entity.getDistractors())) // 선택지 리스트 설정
                .build();
    }

    /**
     * 선택지 리스트를 매핑하는 메서드
     */
    private List<ImageDistractorResponseDto> toDistractorResponseDtoList(List<ImagequizDistractor> distractors) {
        return distractors.stream()
                .map(distractor -> ImageDistractorResponseDto.builder()
                        .imageQuizDistractor(String.valueOf(distractor.getImageDistractorId()))
                        .imageQuizDistractor(distractor.getImageDistractor())
                        .validation(distractor.isValidation())
                        .build())
                .collect(Collectors.toList());
    }
}

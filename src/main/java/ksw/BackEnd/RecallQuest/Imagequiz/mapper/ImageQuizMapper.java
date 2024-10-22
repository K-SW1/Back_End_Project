package ksw.BackEnd.RecallQuest.Imagequiz.mapper;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
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
}

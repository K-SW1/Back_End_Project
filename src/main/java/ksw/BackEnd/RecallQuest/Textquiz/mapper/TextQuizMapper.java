package ksw.BackEnd.RecallQuest.Textquiz.mapper;

import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TextQuizMapper {
    public TextQuizResponseDto toDto(TextQuiz entity) {
        return TextQuizResponseDto.builder()
                .textQuizId(entity.getTextQuizId())
                .question(entity.getQuestion())
                .hint(entity.getHint())
                .build();
    }

    public List<TextQuizResponseDto> toDtoList(List<TextQuiz> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}


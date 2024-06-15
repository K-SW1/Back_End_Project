package ksw.BackEnd.RecallQuest.Textquiz.mapper;

import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TextQuizMapper {
    public TextQuizResponseDto toDto(TextQuiz entity) throws IOException {
        return TextQuizResponseDto.builder()
                .textQuizId(entity.getTextQuizId())
                .question(entity.getQuestion())
                .hint(entity.getHint())
                .build();
    }

    public TextQuizResponseDto toDtoFromDto(TextQuizResponseDto updatedTextQuiz) throws IOException {
        return TextQuizResponseDto.builder()
                .textQuizId(updatedTextQuiz.getTextQuizId())
                .question(updatedTextQuiz.getQuestion())
                .hint(updatedTextQuiz.getHint())
                .build();
    }
}


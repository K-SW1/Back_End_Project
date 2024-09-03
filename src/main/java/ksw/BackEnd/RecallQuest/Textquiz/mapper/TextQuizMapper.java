package ksw.BackEnd.RecallQuest.Textquiz.mapper;

import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class TextQuizMapper {

    /**
     * 응답 매퍼
     */
    public TextQuizResponseDto toDto(TextQuiz entity) {
        return TextQuizResponseDto.builder()
                .textQuizId(entity.getTextQuizId())
                .memberSeq(entity.getMember().getMemberSeq()) // memberSeq 설정
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


package ksw.BackEnd.RecallQuest.Textquizdistractor.mapper;

import ksw.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorRequestDto;
import ksw.BackEnd.RecallQuest.entity.TextDistractor;
import ksw.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class TextDistractorMapper {

    public TextDistractorResponseDto toDto(TextDistractor textDistractor) {
        return TextDistractorResponseDto.builder()
                .textzQuizDistractor(textDistractor.getTextzQuizDistractor())
                .validation(textDistractor.isValidation())
                .build();
    }

    public List<TextDistractorResponseDto> toDtoList(List<TextDistractor> textDistractors) {
        return textDistractors.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TextDistractorRequestDto toRequestDto(TextDistractor textDistractor) {
        return TextDistractorRequestDto.builder()
                .textzQuizDistractor(textDistractor.getTextzQuizDistractor())
                .validation(textDistractor.isValidation())
                .build();
    }

    public List<TextDistractorRequestDto> toRequestDtoList(List<TextDistractor> textDistractors) {
        return textDistractors.stream()
                .map(this::toRequestDto)
                .collect(Collectors.toList());
    }

}


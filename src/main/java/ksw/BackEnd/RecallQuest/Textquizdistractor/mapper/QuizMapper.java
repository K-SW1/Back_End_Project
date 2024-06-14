package ksw.BackEnd.RecallQuest.Textquizdistractor.mapper;


import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import ksw.BackEnd.RecallQuest.entity.TextDistractor;

import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizRequestDto;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;
import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizWithDistractorsResponseDto;
import ksw.BackEnd.RecallQuest.Textquiz.service.TextQuizService;

import ksw.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorRequestDto;
import ksw.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorResponseDto;
import ksw.BackEnd.RecallQuest.Textquizdistractor.service.TextDistractorService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class QuizMapper {

    public TextDistractorResponseDto toResponseDto(TextDistractor textDistractor) {

        return TextDistractorResponseDto.builder()
                .textzQuizDistractor(textDistractor.getTextzQuizDistractor())
                .validation(textDistractor.isValidation())
                .build();
    }

}


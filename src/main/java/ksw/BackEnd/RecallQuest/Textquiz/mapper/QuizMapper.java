package ksw.BackEnd.RecallQuest.Textquiz.mapper;

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

    public TextQuiz mapToEntity(TextQuizRequestDto requestDto) {

        // TextQuizRequestDto에서 TextQuiz 엔티티로의 매핑 수행
        TextQuiz textQuiz = new TextQuiz();
        textQuiz.setQuestion(requestDto.getQuestion());
        textQuiz.setHint(requestDto.getHint());

        return textQuiz;
    }

    public TextQuizResponseDto mapToResponseDto(TextQuiz textQuiz) {

        // TextQuiz 엔티티에서 TextQuizResponseDto로의 매핑 수행
        TextQuizResponseDto responseDto = new TextQuizResponseDto();
        responseDto.setTextQuizId(textQuiz.getTextQuizId());
        responseDto.setQuestion(textQuiz.getQuestion());
        responseDto.setHint(textQuiz.getHint());

        return responseDto;
    }

}

// TextQuizRequestDto를 사용하여 TextQuiz 엔티티를 생성, TextQuiz 엔티티를 사용하여 TextQuizResponseDto를 생성
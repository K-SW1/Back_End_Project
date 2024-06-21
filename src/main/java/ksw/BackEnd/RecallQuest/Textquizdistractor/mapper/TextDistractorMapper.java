package ksw.BackEnd.RecallQuest.Textquizdistractor.mapper;

import ksw.BackEnd.RecallQuest.Textquiz.dto.TextQuizWithDistractorsResponseDto;
import ksw.BackEnd.RecallQuest.Textquizdistractor.dao.JpaTextDistractorDao;
import ksw.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorRequestDto;
import ksw.BackEnd.RecallQuest.entity.TextDistractor;
import ksw.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorResponseDto;
import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class TextDistractorMapper {

    private final JpaTextDistractorDao jpaTextDistractorDao;

    /**
     * 응답 매퍼
     */
    public TextDistractorResponseDto toResponse(TextDistractor textDistractor) {
        return TextDistractorResponseDto.builder()
                .textzQuizDistractor(textDistractor.getTextzQuizDistractor())
                .validation(textDistractor.isValidation())
                .build();
    }

    public TextQuizWithDistractorsResponseDto toResponseDto(TextQuiz textQuiz) {
        List<TextDistractor> textDistractors = jpaTextDistractorDao.findByTextQuiz_TextQuizId(textQuiz.getTextQuizId());
        List<TextDistractorResponseDto> distractorResponseDtos = textDistractors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return TextQuizWithDistractorsResponseDto.builder()
                .textQuizId(textQuiz.getTextQuizId())
                .question(textQuiz.getQuestion())
                .hint(textQuiz.getHint())
                .Distractors(distractorResponseDtos)
                .build();
    }

    public List<TextDistractorResponseDto> toResponseDistractorList(List<TextDistractor> textDistractors) {
        return textDistractors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    public List<TextQuizWithDistractorsResponseDto> toResponseTextQuizList(List<TextQuiz> textQuizzes) {
        return textQuizzes.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }



    /**
     * 요청 매퍼
     */
    public TextDistractorRequestDto toRequestDistractor(TextDistractor textDistractor) {
        return TextDistractorRequestDto.builder()
                .textzQuizDistractor(textDistractor.getTextzQuizDistractor())
                .validation(textDistractor.isValidation())
                .build();
    }

    public List<TextDistractorRequestDto> toRequestDistractorList(List<TextDistractor> textDistractors) {
        return textDistractors.stream()
                .map(this::toRequestDistractor)
                .collect(Collectors.toList());
    }

    
    
    


}


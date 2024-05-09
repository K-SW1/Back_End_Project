package csj.BackEnd.RecallQuest.Textquizdistractor.service;

import csj.BackEnd.RecallQuest.Textquiz.dao.JpaTextQuizDao;
import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizWithDistractorsResponseDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.dao.JpaTextDistractorDao;
import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorRequestDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextDistractorResponseDto;

import csj.BackEnd.RecallQuest.entity.TextDistractor;
import csj.BackEnd.RecallQuest.entity.TextQuiz;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TextDistractorService {


    private final JpaTextQuizDao jpaTextQuizDao;
    private final JpaTextDistractorDao jpaTextDistractorDao;





    // [TextQuiz](선택지)(정답) 추가 서비스
    public List<TextDistractor> addTextDistractorsToQuiz(int textQuizId, List<TextDistractorRequestDto> requestDtos) {
        // 텍스트 퀴즈 ID에 해당하는 텍스트 퀴즈 엔티티를 가져옵니다.
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // TextDistractorRequestDto를 TextDistractor 엔티티로 변환하여 저장합니다.
        List<TextDistractor> Distractors = requestDtos.stream()
                .map(requestDto -> {
                    TextDistractor Distractor = new TextDistractor();
                    Distractor.setTextzQuizDistractor(requestDto.getTextzQuizDistractor());
                    Distractor.setValidation(requestDto.isValidation());
                    // 텍스트 퀴즈 엔티티를 설정합니다.
                    Distractor.setTextQuiz(textQuiz);
                    return Distractor;
                })
                .collect(Collectors.toList());

        // 저장된 선택지 엔티티들을 반환합니다.
        return jpaTextDistractorDao.saveAll(Distractors);
    }



    // [TextQuiz](힌트)(선택지)(정답) 특정 조회 서비스
    public TextQuizWithDistractorsResponseDto getTextQuizWithDistractors(int textQuizId) {
        // ID로 텍스트 퀴즈를 가져옵니다.
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // 텍스트 퀴즈에 해당하는 선택지를 가져옵니다.
        List<TextDistractorResponseDto> Distractors = getTextDistractorsByQuizId(textQuizId);

        // 텍스트 퀴즈와 선택지를 포함하는 응답 DTO를 생성합니다.
        TextQuizWithDistractorsResponseDto responseDto = new TextQuizWithDistractorsResponseDto();
        responseDto.setTextQuizId(textQuiz.getTextQuizId());
        responseDto.setQuestion(textQuiz.getQuestion());
        responseDto.setHint(textQuiz.getHint());
        responseDto.setDistractors(Distractors);

        return responseDto;
    }

    // [TextQuiz](힌트)(선택지)(정답) 전체 조회 서비스
    public List<TextQuizWithDistractorsResponseDto> getAllTextQuizzesWithDistractors() {
        // 모든 텍스트 퀴즈를 가져옵니다.
        List<TextQuiz> allTextQuizzes = jpaTextQuizDao.findAll();

        // 각 텍스트 퀴즈에 대해 선택지를 가져와서 DTO로 만듭니다.
        List<TextQuizWithDistractorsResponseDto> responseDtos = new ArrayList<>();
        for (TextQuiz textQuiz : allTextQuizzes) {
            TextQuizWithDistractorsResponseDto responseDto = new TextQuizWithDistractorsResponseDto();
            responseDto.setTextQuizId(textQuiz.getTextQuizId());
            responseDto.setQuestion(textQuiz.getQuestion());
            responseDto.setHint(textQuiz.getHint());
            // 각 퀴즈에 대한 선택지를 가져옵니다.
            List<TextDistractorResponseDto> distractors = getTextDistractorsByQuizId(textQuiz.getTextQuizId());
            responseDto.setDistractors(distractors);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }





    // [TextQuiz](선택지)(정답) 조회 서비스
    public List<TextDistractorResponseDto> getTextDistractorsByQuizId(int textQuizId) {
        // 텍스트 퀴즈 ID에 해당하는 선택지를 데이터베이스에서 조회
        List<TextDistractor> textDistractors = jpaTextDistractorDao.findByTextQuiz_TextQuizId(textQuizId);
        // 조회된 선택지를 DTO 형식으로 변환하여 반환
        return convertToResponseDtos(textDistractors);
    }
    // TextDistractor 엔티티를 TextDistractorResponseDto로 변환하는 메서드
    private List<TextDistractorResponseDto> convertToResponseDtos(List<TextDistractor> textDistractors) {
        // 변환한 DTO들을 담을 리스트를 생성합니다.
        List<TextDistractorResponseDto> dtos = new ArrayList<>();

        // 선택지 리스트를 순회하면서 각 선택지를 DTO로 변환합니다.
        for (TextDistractor textDistractor : textDistractors) {
            // 선택지 엔티티를 DTO로 변환하여 리스트에 추가합니다.
            dtos.add(convertToResponseDto(textDistractor));
        }

        // 모든 선택지에 대한 DTO를 담은 리스트를 반환합니다.
        return dtos;
    }
    // TextDistractor 엔티티를 TextDistractorResponseDto로 변환하는 메서드
    private TextDistractorResponseDto convertToResponseDto(TextDistractor textDistractor) {
        TextDistractorResponseDto responseDto = new TextDistractorResponseDto();
        responseDto.setTextzQuizDistractor(textDistractor.getTextzQuizDistractor());
        responseDto.setValidation(textDistractor.isValidation());
        return responseDto;
    }




    // [TextQuiz](선택지) 수정 서비스
    @Transactional
    public List<TextDistractorResponseDto> updateTextDistractors(int textQuizId, List<TextDistractorRequestDto> updatedDistractorsRequestDto) {
        // 텍스트 퀴즈 ID로 해당하는 텍스트 퀴즈를 찾습니다.
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // 기존의 선택지를 모두 삭제합니다.
        List<TextDistractor> existingDistractors = jpaTextDistractorDao.findByTextQuiz_TextQuizId(textQuizId);
        jpaTextDistractorDao.deleteAll(existingDistractors);

        // 새로운 선택지들을 텍스트 퀴즈에 추가합니다.
        List<TextDistractor> updatedDistractors = new ArrayList<>();
        for (TextDistractorRequestDto distractorRequestDto : updatedDistractorsRequestDto) {
            TextDistractor distractor = new TextDistractor();
            distractor.setTextzQuizDistractor(distractorRequestDto.getTextzQuizDistractor());
            distractor.setValidation(distractorRequestDto.isValidation());
            distractor.setTextQuiz(textQuiz);
            updatedDistractors.add(distractor);
        }
        List<TextDistractor> savedDistractors = jpaTextDistractorDao.saveAll(updatedDistractors);

        // 수정된 선택지들을 응답 DTO로 변환하여 반환합니다.
        return savedDistractors.stream()
                .map(distractor -> {
                    TextDistractorResponseDto responseDto = new TextDistractorResponseDto();
                    responseDto.setTextzQuizDistractor(distractor.getTextzQuizDistractor());
                    responseDto.setValidation(distractor.isValidation());
                    return responseDto;
                })
                .collect(Collectors.toList());
    }











}


package csj.BackEnd.RecallQuest.Textquizdistractor.service;

import csj.BackEnd.RecallQuest.Textquiz.dao.JpaTextQuizDao;
import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizWithChoicesResponseDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.dao.JpaTextChoiceDao;
import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextChoiceRequestDto;
import csj.BackEnd.RecallQuest.Textquizdistractor.dto.TextChoiceResponseDto;

import csj.BackEnd.RecallQuest.entity.TextChoice;
import csj.BackEnd.RecallQuest.entity.TextQuiz;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TextChoiceService {


    private final JpaTextQuizDao jpaTextQuizDao;
    private final JpaTextChoiceDao jpaTextChoiceDao;





    // [TextQuiz](선택지)(정답) 추가 서비스
    public List<TextChoice> addTextChoicesToQuiz(int textQuizId, List<TextChoiceRequestDto> requestDtos) {
        // 텍스트 퀴즈 ID에 해당하는 텍스트 퀴즈 엔티티를 가져옵니다.
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // TextChoiceRequestDto를 TextChoice 엔티티로 변환하여 저장합니다.
        List<TextChoice> choices = requestDtos.stream()
                .map(requestDto -> {
                    TextChoice choice = new TextChoice();
                    choice.setChoiceText(requestDto.getChoiceText());
                    choice.setAnswer(requestDto.isAnswer());
                    // 텍스트 퀴즈 엔티티를 설정합니다.
                    choice.setTextQuiz(textQuiz);
                    return choice;
                })
                .collect(Collectors.toList());

        // 저장된 선택지 엔티티들을 반환합니다.
        return jpaTextChoiceDao.saveAll(choices);
    }



    // [TextQuiz](힌트)(선택지)(정답) 특정 조회 서비스
    public TextQuizWithChoicesResponseDto getTextQuizWithChoices(int textQuizId) {
        // ID로 텍스트 퀴즈를 가져옵니다.
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // 텍스트 퀴즈에 해당하는 선택지를 가져옵니다.
        List<TextChoiceResponseDto> choices = getTextChoicesByQuizId(textQuizId);

        // 텍스트 퀴즈와 선택지를 포함하는 응답 DTO를 생성합니다.
        TextQuizWithChoicesResponseDto responseDto = new TextQuizWithChoicesResponseDto();
        responseDto.setTextQuizId(textQuiz.getTextQuizId());
        responseDto.setQuestion(textQuiz.getQuestion());
        responseDto.setHint(textQuiz.getHint());
        responseDto.setChoices(choices);

        return responseDto;
    }

    // [TextQuiz](힌트)(선택지)(정답) 전체 조회 서비스
    public List<TextQuizWithChoicesResponseDto> getAllTextQuizzesWithChoices() {
        // 모든 텍스트 퀴즈를 가져옵니다.
        List<TextQuiz> allTextQuizzes = jpaTextQuizDao.findAll();

        // 각 텍스트 퀴즈에 대해 선택지를 가져와서 DTO로 만듭니다.
        List<TextQuizWithChoicesResponseDto> responseDtos = new ArrayList<>();
        for (TextQuiz textQuiz : allTextQuizzes) {
            TextQuizWithChoicesResponseDto responseDto = new TextQuizWithChoicesResponseDto();
            responseDto.setTextQuizId(textQuiz.getTextQuizId());
            responseDto.setQuestion(textQuiz.getQuestion());
            responseDto.setHint(textQuiz.getHint());
            // 각 퀴즈에 대한 선택지를 가져옵니다.
            List<TextChoiceResponseDto> choices = getTextChoicesByQuizId(textQuiz.getTextQuizId());
            responseDto.setChoices(choices);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }





    // [TextQuiz](선택지)(정답) 조회 서비스
    public List<TextChoiceResponseDto> getTextChoicesByQuizId(int textQuizId) {
        // 텍스트 퀴즈 ID에 해당하는 선택지를 데이터베이스에서 조회
        List<TextChoice> textChoices = jpaTextChoiceDao.findByTextQuiz_TextQuizId(textQuizId);
        // 조회된 선택지를 DTO 형식으로 변환하여 반환
        return convertToResponseDtos(textChoices);
    }
    // TextChoice 엔티티를 TextChoiceResponseDto로 변환하는 메서드
    private List<TextChoiceResponseDto> convertToResponseDtos(List<TextChoice> textChoices) {
        // 변환한 DTO들을 담을 리스트를 생성합니다.
        List<TextChoiceResponseDto> dtos = new ArrayList<>();

        // 선택지 리스트를 순회하면서 각 선택지를 DTO로 변환합니다.
        for (TextChoice textChoice : textChoices) {
            // 선택지 엔티티를 DTO로 변환하여 리스트에 추가합니다.
            dtos.add(convertToResponseDto(textChoice));
        }

        // 모든 선택지에 대한 DTO를 담은 리스트를 반환합니다.
        return dtos;
    }
    // TextChoice 엔티티를 TextChoiceResponseDto로 변환하는 메서드
    private TextChoiceResponseDto convertToResponseDto(TextChoice textChoice) {
        TextChoiceResponseDto responseDto = new TextChoiceResponseDto();
        responseDto.setChoiceText(textChoice.getChoiceText());
        responseDto.setAnswer(textChoice.isAnswer());
        return responseDto;
    }




    // [TextQuiz](선택지) 수정 서비스
    @Transactional
    public List<TextChoiceResponseDto> updateTextChoices(int textQuizId, List<TextChoiceRequestDto> updatedChoicesRequestDto) {
        // 텍스트 퀴즈 ID로 해당하는 텍스트 퀴즈를 찾습니다.
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // 기존의 선택지를 모두 삭제합니다.
        List<TextChoice> existingChoices = jpaTextChoiceDao.findByTextQuiz_TextQuizId(textQuizId);
        jpaTextChoiceDao.deleteAll(existingChoices);

        // 새로운 선택지들을 텍스트 퀴즈에 추가합니다.
        List<TextChoice> updatedChoices = new ArrayList<>();
        for (TextChoiceRequestDto choiceRequestDto : updatedChoicesRequestDto) {
            TextChoice choice = new TextChoice();
            choice.setChoiceText(choiceRequestDto.getChoiceText());
            choice.setAnswer(choiceRequestDto.isAnswer());
            choice.setTextQuiz(textQuiz);
            updatedChoices.add(choice);
        }
        List<TextChoice> savedChoices = jpaTextChoiceDao.saveAll(updatedChoices);

        // 수정된 선택지들을 응답 DTO로 변환하여 반환합니다.
        return savedChoices.stream()
                .map(choice -> {
                    TextChoiceResponseDto responseDto = new TextChoiceResponseDto();
                    responseDto.setChoiceText(choice.getChoiceText());
                    responseDto.setAnswer(choice.isAnswer());
                    return responseDto;
                })
                .collect(Collectors.toList());
    }











}


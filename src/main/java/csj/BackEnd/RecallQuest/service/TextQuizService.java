package csj.BackEnd.RecallQuest.service;

import csj.BackEnd.RecallQuest.dao.JpaTextChoiceDao;
import csj.BackEnd.RecallQuest.dao.JpaTextQuizDao;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import csj.BackEnd.RecallQuest.domain.TextQuiz;
import csj.BackEnd.RecallQuest.domain.TextChoice;
import csj.BackEnd.RecallQuest.dto.TextQuizRequestDto;
import csj.BackEnd.RecallQuest.dto.TextQuizResponseDto;
import csj.BackEnd.RecallQuest.dto.TextChoiceRequestDto;
import csj.BackEnd.RecallQuest.dto.TextChoiceResponseDto;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class TextQuizService {


    private final JpaTextQuizDao jpaTextQuizDao;
    private final JpaTextChoiceDao jpaTextChoiceDao;




    // [TextQuiz]추가 서비스
    public TextQuizResponseDto addTextQuiz(TextQuizRequestDto requestDto) {
        // 요청 DTO를 엔티티로 변환합니다. requestDto을 통해서 전달만 해주는 거고, 다시 Entity를 통해서 값을 추가 해 주어야 함.
        TextQuiz textQuiz = new TextQuiz();
        textQuiz.setMemberInfoId(requestDto.getMemberInfoId());
        textQuiz.setQuestion(requestDto.getQuestion());
        textQuiz.setHint(requestDto.getHint());

        // 엔티티를 저장하고 생성된 ID를 받아옵니다
        TextQuiz savedTextQuiz = jpaTextQuizDao.save(textQuiz);

        // 엔티티를 응답 DTO로 변환합니다
        TextQuizResponseDto responseDto = new TextQuizResponseDto();
        responseDto.setTextQuizId(savedTextQuiz.getTextQuizId());
        responseDto.setMemberInfoId(savedTextQuiz.getMemberInfoId());
        responseDto.setQuestion(savedTextQuiz.getQuestion());
        responseDto.setHint(savedTextQuiz.getHint());

        return responseDto;
    }

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






    // [TextQuiz]조회 서비스
    public List<TextQuiz> getAllTextQuizzes() {
        return jpaTextQuizDao.findAll();
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







    // [TextQuiz] 수정 서비스
    public TextQuizResponseDto updateTextQuiz(int textQuizId, TextQuizRequestDto updatedTextQuizRequestDto) {
        // 텍스트 퀴즈 ID에 해당하는 텍스트 퀴즈를 가져옴
        TextQuiz existingTextQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // 업데이트할 내용을 새로운 값으로 설정
        existingTextQuiz.setQuestion(updatedTextQuizRequestDto.getQuestion());
        existingTextQuiz.setHint(updatedTextQuizRequestDto.getHint());

        // 업데이트된 텍스트 퀴즈를 저장하고 반환
        TextQuiz updatedTextQuiz = jpaTextQuizDao.save(existingTextQuiz);

        // 엔티티를 DTO로 변환하여 반환
        return convertToResponseDto(updatedTextQuiz);
    }
    // TextQuiz 엔티티를 TextQuizResponseDto로 변환하는 메서드
    private TextQuizResponseDto convertToResponseDto(TextQuiz textQuiz) {
        TextQuizResponseDto responseDto = new TextQuizResponseDto();
        responseDto.setTextQuizId(textQuiz.getTextQuizId());
        responseDto.setQuestion(textQuiz.getQuestion());
        responseDto.setHint(textQuiz.getHint());
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





    // [TextQuiz][TextChoice] 삭제 서비스 //[TextChoice]먼저 삭제 후 [TextQuiz] 삭제 되는 방식. 외래키 제약 조건 //트래젝션은 두가지 일을 하나로 묶어서 진행하되, 중간에 오류 발생 시 롤백.
    @Transactional
    public void deleteTextQuiz(int textQuizId) {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // 텍스트 퀴즈와 관련된 선택지들을 먼저 삭제합니다.
        jpaTextChoiceDao.deleteByTextQuiz(textQuiz);

        // 이후 텍스트 퀴즈를 삭제합니다.
        jpaTextQuizDao.delete(textQuiz);
    }






}


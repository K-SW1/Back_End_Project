package csj.BackEnd.RecallQuest.Textquiz.service;

import csj.BackEnd.RecallQuest.Textquizdistractor.dao.JpaTextChoiceDao;
import csj.BackEnd.RecallQuest.Textquiz.dao.JpaTextQuizDao;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import csj.BackEnd.RecallQuest.entity.TextQuiz;

import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizRequestDto;
import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;


import lombok.RequiredArgsConstructor;
import java.util.List;


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




    // [TextQuiz]조회 서비스
    public List<TextQuiz> getAllTextQuizzes() {
        return jpaTextQuizDao.findAll();
    }


    // [TextQuiz] 특정 조회 서비스
    public TextQuizResponseDto getTextQuizById(int textQuizId) {
        // 특정 ID에 해당하는 텍스트 퀴즈를 가져옵니다.
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // TextQuiz 엔티티를 TextQuizResponseDto로 변환하여 반환합니다.
        return convertToResponseDto(textQuiz);
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


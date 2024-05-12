package csj.BackEnd.RecallQuest.Textquiz.service;

import csj.BackEnd.RecallQuest.Textquiz.dao.JpaTextQuizDao;
import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizRequestDto;
import csj.BackEnd.RecallQuest.Textquiz.dto.TextQuizResponseDto;
import csj.BackEnd.RecallQuest.entity.TextQuiz;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextQuizService {

    private final JpaTextQuizDao jpaTextQuizDao;

    // [TextQuiz] 추가 서비스
    public TextQuizResponseDto addTextQuiz(TextQuizRequestDto requestDto) {
        TextQuiz textQuiz = TextQuiz.builder()
                .memberInfoId(requestDto.getMemberInfoId())
                .question(requestDto.getQuestion())
                .hint(requestDto.getHint())
                .build();

        TextQuiz savedTextQuiz = jpaTextQuizDao.save(textQuiz);

        return TextQuizResponseDto.builder()
                .textQuizId(savedTextQuiz.getTextQuizId())
                .memberInfoId(savedTextQuiz.getMemberInfoId())
                .question(savedTextQuiz.getQuestion())
                .hint(savedTextQuiz.getHint())
                .build();
    }

    // [TextQuiz] 조회 서비스
    public List<TextQuiz> getAllTextQuizzes() {
        return jpaTextQuizDao.findAll();
    }

    // [TextQuiz] 특정 조회 서비스
    public TextQuizResponseDto getTextQuizById(int textQuizId) {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        return TextQuizResponseDto.builder()
                .textQuizId(textQuiz.getTextQuizId())
                .memberInfoId(textQuiz.getMemberInfoId())
                .question(textQuiz.getQuestion())
                .hint(textQuiz.getHint())
                .build();
    }

    // [TextQuiz] 수정 서비스
    public TextQuizResponseDto updateTextQuiz(int textQuizId, TextQuizRequestDto updatedTextQuizRequestDto) {
        TextQuiz existingTextQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        existingTextQuiz.setQuestion(updatedTextQuizRequestDto.getQuestion());
        existingTextQuiz.setHint(updatedTextQuizRequestDto.getHint());

        TextQuiz updatedTextQuiz = jpaTextQuizDao.save(existingTextQuiz);

        return TextQuizResponseDto.builder()
                .textQuizId(updatedTextQuiz.getTextQuizId())
                .memberInfoId(updatedTextQuiz.getMemberInfoId())
                .question(updatedTextQuiz.getQuestion())
                .hint(updatedTextQuiz.getHint())
                .build();
    }

    // [TextQuiz][TextChoice] 삭제 서비스 //[TextChoice]먼저 삭제 후 [TextQuiz] 삭제 되는 방식. 외래키 제약 조건 //트래젝션은 두가지 일을 하나로 묶어서 진행하되, 중간에 오류 발생 시 롤백.
    @Transactional
    public void deleteTextQuiz(int textQuizId) {
        TextQuiz textQuiz = jpaTextQuizDao.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        jpaTextQuizDao.delete(textQuiz);
    }

}

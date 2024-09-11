package ksw.BackEnd.RecallQuest.Textquizdistractor.dao;


import ksw.BackEnd.RecallQuest.common.Exception.textquiz.TextQuizNotFoundException;
import ksw.BackEnd.RecallQuest.entity.TextDistractor;
import ksw.BackEnd.RecallQuest.Textquizdistractor.repository.TextDistractorRepository;
import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import ksw.BackEnd.RecallQuest.common.Exception.TextQuizDistractor.TextQuizDistractorNotFoundException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaTextDistractorDao {

    private final TextDistractorRepository textdistractorRepository;

    public TextDistractor save(TextDistractor textdistractor) {
        return textdistractorRepository.save(textdistractor);
    }

    public List<TextDistractor> saveAll(List<TextDistractor> textDistractors) {
        return textdistractorRepository.saveAll(textDistractors);
    }


//특정 텍스트 퀴즈 ID에 해당하는 선택지 목록을 가져오는 메서드
//    public List<TextDistractor> findByTextQuiz_TextQuizId(int textQuizId) {
//        List<TextDistractor> distractors = textdistractorRepository.findByTextQuiz_TextQuizId(textQuizId);
//        if (distractors.isEmpty()) {
//            throw new TextQuizDistractorNotFoundException("해당 TextQuiz ID에 대한 선택지를 찾을 수 없습니다: ");
//        }
//        return distractors;
//    }


    // 빈 객체로 반환
    public List<TextDistractor> findByTextQuiz_TextQuizId(int textQuizId) {
        return textdistractorRepository.findByTextQuiz_TextQuizId(textQuizId);
    }



    public void deleteAll(List<TextDistractor> textdistractors) {
        textdistractorRepository.deleteAll(textdistractors);
    }

    public TextDistractor deleteByTextDistractorId(TextDistractor textDistractorId) {
        textdistractorRepository.deleteBytextDistractorId(textDistractorId.getTextDistractorId());
        return null;
    }


    public TextDistractor findById(int textDistractorId) {
        return textdistractorRepository.findById(textDistractorId)
                .orElseThrow(() -> new TextQuizNotFoundException("해당 ID의 텍스트퀴즈를 찾을 수 없습니다"));
    }

}

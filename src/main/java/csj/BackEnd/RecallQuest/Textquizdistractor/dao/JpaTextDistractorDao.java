package csj.BackEnd.RecallQuest.Textquizdistractor.dao;


import csj.BackEnd.RecallQuest.entity.TextDistractor;
import csj.BackEnd.RecallQuest.entity.TextQuiz;
import csj.BackEnd.RecallQuest.Textquizdistractor.repository.TextDistractorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaTextDistractorDao {

    private final TextDistractorRepository textdistractorRepository;

    public TextDistractor save(TextDistractor textdistractor) {
        return textdistractorRepository.save(textdistractor);
    }

    // 특정 텍스트 퀴즈 ID에 해당하는 선택지 목록을 가져오는 메서드
    public List<TextDistractor> findByTextQuiz_TextQuizId(int textQuizId) {
        return textdistractorRepository.findByTextQuiz_TextQuizId(textQuizId);
    }

    // 특정 텍스트 퀴즈와 관련된 선택지를 삭제하는 메서드
    public void deleteByTextQuiz(TextQuiz textQuiz) {
        textdistractorRepository.deleteByTextQuiz(textQuiz);
    }


    // 밑에 두 메서드는 서비스에서 변수 선언하여 사용하고 있음.
    public List<TextDistractor> saveAll(List<TextDistractor> textdistractors) {
        return textdistractorRepository.saveAll(textdistractors);
    }

    public void deleteAll(List<TextDistractor> textdistractors) {
        textdistractorRepository.deleteAll(textdistractors);
    }


}

//textChoiceRepository
//textChoice
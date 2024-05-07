package csj.BackEnd.RecallQuest.Textquizdistractor.dao;


import csj.BackEnd.RecallQuest.entity.TextChoice;
import csj.BackEnd.RecallQuest.entity.TextQuiz;
import csj.BackEnd.RecallQuest.Textquizdistractor.repository.TextChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaTextChoiceDao {

    private final TextChoiceRepository textChoiceRepository;

    public TextChoice save(TextChoice textChoice) {
        return textChoiceRepository.save(textChoice);
    }

    // 특정 텍스트 퀴즈 ID에 해당하는 선택지 목록을 가져오는 메서드
    public List<TextChoice> findByTextQuiz_TextQuizId(int textQuizId) {
        return textChoiceRepository.findByTextQuiz_TextQuizId(textQuizId);
    }

    // 특정 텍스트 퀴즈와 관련된 선택지를 삭제하는 메서드
    public void deleteByTextQuiz(TextQuiz textQuiz) {
        textChoiceRepository.deleteByTextQuiz(textQuiz);
    }


    // 밑에 두 메서드는 서비스에서 변수 선언하여 사용하고 있음.
    public List<TextChoice> saveAll(List<TextChoice> textChoices) {
        return textChoiceRepository.saveAll(textChoices);
    }

    public void deleteAll(List<TextChoice> textChoices) {
        textChoiceRepository.deleteAll(textChoices);
    }


}

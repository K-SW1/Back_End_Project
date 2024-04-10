package csj.BackEnd.RecallQuest.dao;

import csj.BackEnd.RecallQuest.domain.TextChoice;
import csj.BackEnd.RecallQuest.domain.TextQuiz;
import csj.BackEnd.RecallQuest.repository.TextChoiceRepository;
import csj.BackEnd.RecallQuest.repository.TextQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaTextQuizDao {

    private final TextQuizRepository textQuizRepository;

    public TextQuiz save(TextQuiz textQuiz) {
        return textQuizRepository.save(textQuiz);
    }

    public Optional<TextQuiz> findById(int textQuizId) {
        return textQuizRepository.findById(textQuizId);
    }


    public TextQuiz findTextQuizBymemberInfoId(long id) {
        return textQuizRepository.findTextQuizBymemberInfoId(id);
    }


    public List<TextQuiz> findAll() {
        return textQuizRepository.findAll();
    }

    public void delete(TextQuiz textQuiz) {
        textQuizRepository.delete(textQuiz);
    }

    public void deleteAll() {
        textQuizRepository.deleteAll();
    }


}

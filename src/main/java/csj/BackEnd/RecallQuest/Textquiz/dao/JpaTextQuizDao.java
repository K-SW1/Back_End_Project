package csj.BackEnd.RecallQuest.Textquiz.dao;

import csj.BackEnd.RecallQuest.entity.Member;
import csj.BackEnd.RecallQuest.entity.TextQuiz;
import csj.BackEnd.RecallQuest.Textquiz.repository.TextQuizRepository;
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


    public TextQuiz findTextQuizBymember(Member member) {
        return textQuizRepository.findTextQuizBymember(member);
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

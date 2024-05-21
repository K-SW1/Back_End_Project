package ksw.BackEnd.RecallQuest.Textquiz.dao;

import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import ksw.BackEnd.RecallQuest.Textquiz.repository.TextQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import ksw.BackEnd.RecallQuest.common.Exception.textquiz.TextQuizNotFoundException;
import ksw.BackEnd.RecallQuest.common.Exception.textquiz.TextQuizAlreadyExistsException;



@Repository
@RequiredArgsConstructor
public class JpaTextQuizDao {

    private final TextQuizRepository textQuizRepository;

    public TextQuiz save(TextQuiz textQuiz) {
        // 동일한 질문이 이미 존재하는지 확인
        if (textQuizRepository.existsByQuestion(textQuiz.getQuestion())) {
            throw new TextQuizAlreadyExistsException("TextQuiz with the same question already exists", 409);
        }
        return textQuizRepository.save(textQuiz);
    }
    public TextQuiz findById(int textQuizId) {
        return textQuizRepository.findById(textQuizId)
                .orElseThrow(() -> new TextQuizNotFoundException("TextQuiz not found with ID: " + textQuizId, 404));
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

//    public boolean existsByQuestion(String question) {
//        return textQuizRepository.existsByQuestion(question);
//    }



}

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
        return textQuizRepository.save(textQuiz);
    }
    public TextQuiz findById(int textQuizId) {
        return textQuizRepository.findById(textQuizId)
                .orElseThrow(() -> new TextQuizNotFoundException("해당 ID의 텍스트퀴즈를 찾을 수 없습니다"));
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

    public boolean existsByQuestion(String question) {
        return textQuizRepository.existsByQuestion(question);
    }

    public TextQuiz findByQuestion(String question) {
        return textQuizRepository.findByQuestion(question)
                .orElseThrow(() -> new TextQuizNotFoundException("해당 질문의 텍스트퀴즈를 찾을 수 없습니다"));
    }
//
    public List<TextQuiz> findAllByMember(Member member) {
        return textQuizRepository.findAllByMember(member);
    }


    public List<TextQuiz> findAllByMember_MemberSeq(Long memberSeq) {
        return textQuizRepository.findAllByMember_MemberSeq(memberSeq);
    }
}

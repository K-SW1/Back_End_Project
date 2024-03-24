package ksw.BackEnd.RecallQuest.repository;

import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
import ksw.BackEnd.RecallQuest.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {
    ImageQuiz findByQuestion(String question);
}

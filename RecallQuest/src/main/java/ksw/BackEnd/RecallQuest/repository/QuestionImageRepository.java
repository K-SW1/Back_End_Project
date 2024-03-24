package ksw.BackEnd.RecallQuest.repository;

import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
import ksw.BackEnd.RecallQuest.domain.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionImageRepository extends JpaRepository<QuestionImage, Long> {

    Optional<QuestionImage> findByOriginFilename(String fileName);

    void deleteByImageQuiz(ImageQuiz imageQuiz);
}

package ksw.BackEnd.RecallQuest.imagequiz.repository;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionImageRepository extends JpaRepository<QuestionImage, Long> {

//    Optional<QuestionImage> findByOriginFilename(String fileName);

    void deleteByImageQuiz(ImageQuiz imageQuiz);

    Optional<QuestionImage> findByStoreFilename(String fileName);
}

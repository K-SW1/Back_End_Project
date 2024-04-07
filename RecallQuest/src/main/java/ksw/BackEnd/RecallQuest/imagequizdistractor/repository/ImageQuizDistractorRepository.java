package ksw.BackEnd.RecallQuest.imagequizdistractor.repository;

import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageQuizDistractorRepository extends JpaRepository<ImageQuizDistractor, Long> {
    ImageQuizDistractor findByImageQuizDistractor(String imageQuizDistractor);

    List<ImageQuizDistractor> findByImageQuizId(Long imageQuizId);
}

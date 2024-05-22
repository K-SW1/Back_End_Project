package ksw.BackEnd.RecallQuest.imagequizdistractor.repository;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.ImageQuizDistractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageQuizDistractorRepository extends JpaRepository<ImageQuizDistractor, Long> {
    Optional<ImageQuizDistractor> findByImageQuizDistractor(String imageQuizDistractor);

    List<ImageQuizDistractor> findByImageQuizImageQuizSeq(Long imageQuizId);

    //이따가 테스트
    @Query("select iqd from ImageQuizDistractor iqd where iqd.imageQuiz.imageQuizSeq = :imageQuizSeq")
    List<ImageQuizDistractor> findByImageQuizSeq(Long imageQuizSeq);

}

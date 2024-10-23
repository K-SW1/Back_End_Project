package ksw.BackEnd.RecallQuest.ImagequizDistractor.repository;

import ksw.BackEnd.RecallQuest.entity.ImagequizDistractor;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageDistractorRepository extends JpaRepository<ImagequizDistractor, Integer> {

    Optional<ImagequizDistractor> findById(int imageDistractorId);

    List<ImagequizDistractor> findByImageQuiz_ImageQuizId(int imageQuizId);

    void deleteByImageQuiz(ImageQuiz imageQuiz); // 이미지 퀴즈와 연관된 선택지를 삭제

    void deleteByImageDistractorId(int imageDistractorId); // ImageDistractor ID로 삭제
}

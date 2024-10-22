package ksw.BackEnd.RecallQuest.Imagequiz.repository;

import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.entity.TextQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageQuizRepository extends JpaRepository<ImageQuiz, Long> {

    Optional<ImageQuiz> findById(long imageQuizId);

    ImageQuiz findImageQuizBymember(Member member); // memberInfoId 값 확인 후 TextQuiz를 조회

    boolean existsByQuestion(String question);

    Optional<ImageQuiz> findByQuestion(String question);

    List<ImageQuiz> findAllByMember(Member member);

    List<ImageQuiz> findAllByMember_MemberSeq(Long memberSeq);
}

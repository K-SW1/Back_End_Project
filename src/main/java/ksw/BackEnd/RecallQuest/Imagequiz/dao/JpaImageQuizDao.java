package ksw.BackEnd.RecallQuest.Imagequiz.dao;

import ksw.BackEnd.RecallQuest.entity.Member;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.Imagequiz.repository.ImageQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import ksw.BackEnd.RecallQuest.common.Exception.Imagequiz.ImageQuizNotFoundException;

@Repository
@RequiredArgsConstructor
public class JpaImageQuizDao {

    private final ImageQuizRepository imageQuizRepository;

    public ImageQuiz save(ImageQuiz imageQuiz) {
        return imageQuizRepository.save(imageQuiz);
    }

    public ImageQuiz findById(long imageQuizId) {
        return imageQuizRepository.findById(imageQuizId)
                .orElseThrow(() -> new ImageQuizNotFoundException("해당 ID의 이미지 퀴즈를 찾을 수 없습니다"));
    }

    public ImageQuiz findImageQuizByMember(Member member) {
        return imageQuizRepository.findImageQuizBymember(member);
    }

    public List<ImageQuiz> findAll() {
        return imageQuizRepository.findAll();
    }

    public void delete(ImageQuiz imageQuiz) {
        imageQuizRepository.delete(imageQuiz);
    }

    public void deleteAll() {
        imageQuizRepository.deleteAll();
    }

    public boolean existsByQuestion(String question) {
        return imageQuizRepository.existsByQuestion(question);
    }

    public ImageQuiz findByQuestion(String question) {
        return imageQuizRepository.findByQuestion(question)
                .orElseThrow(() -> new ImageQuizNotFoundException("해당 질문의 이미지 퀴즈를 찾을 수 없습니다"));
    }

    public List<ImageQuiz> findAllByMember(Member member) {
        return imageQuizRepository.findAllByMember(member);
    }

    public List<ImageQuiz> findAllByMember_MemberSeq(Long memberSeq) {
        return imageQuizRepository.findAllByMember_MemberSeq(memberSeq);
    }

}

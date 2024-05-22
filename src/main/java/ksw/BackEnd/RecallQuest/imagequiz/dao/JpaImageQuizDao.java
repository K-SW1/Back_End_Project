package ksw.BackEnd.RecallQuest.imagequiz.dao;

import ksw.BackEnd.RecallQuest.common.Exception.imagequiz.ImageQuizNotFoundException;
import ksw.BackEnd.RecallQuest.entity.ImageQuiz;
import ksw.BackEnd.RecallQuest.imagequiz.repository.ImageQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaImageQuizDao implements ImageQuizDao{

    private final ImageQuizRepository imageQuizRepository;
    @Override
    public ImageQuiz save(ImageQuiz imageQuiz) {
        return imageQuizRepository.save(imageQuiz);
    }

    @Override
    public ImageQuiz findByQuestion(String question) {
        return imageQuizRepository.findByQuestion(question).orElseThrow(()-> new ImageQuizNotFoundException("존재하지 않는 이미지 퀴즈 입니다."));
    }

    @Override
    public ImageQuiz findById(Long imageQuizSeq) {
        return imageQuizRepository.findById(imageQuizSeq).orElseThrow(()-> new ImageQuizNotFoundException("존재하지 않는 이미지 퀴즈 입니다."));
    }

    @Override
    public List<ImageQuiz> findAll() {
        List<ImageQuiz> ImageQuizzes = imageQuizRepository.findAll();
        return ImageQuizzes;
    }

    @Override
    public List<ImageQuiz> findByMemberSeq(Long memberSeq) {
        List<ImageQuiz> ImageQuizzes = imageQuizRepository.findByMemberSeq(memberSeq);
        return ImageQuizzes;
    }

    @Override
    public List<ImageQuiz> findByUserLoginId(String userLoginId) {
        List<ImageQuiz> ImageQuizzes = imageQuizRepository.findByUserLoginId(userLoginId);
        return ImageQuizzes;
    }

    @Override
    public void delete(ImageQuiz imageQuiz) {
        imageQuizRepository.delete(imageQuiz);
    }

    @Override
    public void delete(Long imageQuizSeq) {
        ImageQuiz imageQuiz = imageQuizRepository.findById(imageQuizSeq).orElseThrow(()-> new ImageQuizNotFoundException("존재하지 않는 이미지 퀴즈 입니다."));
        imageQuizRepository.delete(imageQuiz);
    }

    @Override
    public Boolean existsByQuestion(String question) {
        return imageQuizRepository.existsByQuestion(question);
    }
}

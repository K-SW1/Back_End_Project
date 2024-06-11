package ksw.BackEnd.RecallQuest.imagequiz.dao;

import ksw.BackEnd.RecallQuest.entity.ImageQuiz;

import java.util.List;

public interface ImageQuizDao {

    ImageQuiz save(ImageQuiz imageQuiz);

    ImageQuiz findByQuestion(String question);

    ImageQuiz findById(Long imageQuizSeq);

    List<ImageQuiz> findAll();

    List<ImageQuiz> findByMemberSeq(Long memberSeq);

    List<ImageQuiz> findByUserLoginId(String userLoginId);

    void delete(ImageQuiz imageQuiz);

    void delete(Long imageQuizSeq);

    Boolean existsByQuestion(String question);

}

package csj.BackEnd.RecallQuest.dao;

import csj.BackEnd.RecallQuest.domain.TextQuiz;

import java.util.List;

public interface TextQuizDao {

    TextQuiz save(TextQuiz textQuiz);

    TextQuiz findByName(String name);

    List<TextQuiz> findAll();


}

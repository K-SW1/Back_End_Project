package csj.BackEnd.RecallQuest.dao;

import csj.BackEnd.RecallQuest.domain.TextQuiz;
import csj.BackEnd.RecallQuest.domain.TextChoice;
import java.util.List;


public interface TextChoiceDao {

    TextChoice save(TextChoice textChoice);

    TextChoice findByName(String name);

    List<TextChoice> findAll();


}

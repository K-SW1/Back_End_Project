package com.example.Back.repository;

import com.example.Back.entity.TextChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextChoiceRepository extends JpaRepository<TextChoice, Integer> {

    TextChoice findTextQuizBytext_quiz_id(Long id);

    void deleteById(Long id);

}

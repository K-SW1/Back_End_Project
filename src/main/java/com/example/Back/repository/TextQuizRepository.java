package com.example.Back.repository;

import com.example.Back.entity.TextQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextQuizRepository extends JpaRepository<TextQuiz, Integer> {
    TextQuiz findTextQuizByMember_info_id(Long id);

    void deleteById(Long id);

}

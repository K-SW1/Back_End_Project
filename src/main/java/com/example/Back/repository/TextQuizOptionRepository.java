package com.example.Back.repository;

import com.example.Back.entity.TextQuizOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextQuizOptionRepository extends JpaRepository<TextQuizOption, Integer> {

}

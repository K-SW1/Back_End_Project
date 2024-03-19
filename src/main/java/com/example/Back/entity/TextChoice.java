package com.example.Back.entity;

import jakarta.persistence.*; //모든 클래스와 인터페이스를 가져와서 사용. TextQuiz테이블 불러옴.
import jakarta.persistence.Entity; //JPA에서 엔티티 클래스를 정의하고 클래스가 데이터베이스의 테이블에 매핑.
import jakarta.persistence.GeneratedValue; //엔티티의 기본 키 값을 자동으로 생성.
import jakarta.persistence.GenerationType; //여기서 열거형을 사용하여 IDENTITY 전략을 사용.
import jakarta.persistence.Id; //엔티티 클래스의 기본 키를 지정.
import lombok.Data; // Lombok 라이브러리 제공(getter, setter, equals, hashCode, toString 등)
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
public class TextChoice {

    
    //기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //.IDENTITY으로 설정하여 auto-increment으로 값 저장됨.
    private int choice_id;

    @ManyToOne //TextQuiz테이블하고 관계가 다대일로 관계.
    @JoinColumn(name = "text_quiz_id")
    private TextQuiz text_quiz_id;

    //선택지
    @Getter
    @Setter
    private String choice_text;

    //정답
    @Getter
    @Setter
    private boolean answer;


}

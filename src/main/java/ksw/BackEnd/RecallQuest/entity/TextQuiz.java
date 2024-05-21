package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*; //모든 클래스와 인터페이스를 가져와서 사용. TextQuiz테이블 불러옴.
import lombok.Data;  // getter, setter, equals, hashCode, toString
//import java.util.ArrayList;
//import java.util.List;
import lombok.*;


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor // Lombok가 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor // Lombok가 모든 필드를 포함하는 생성자를 생성합니다
@Builder
@Table(name = "textquiz")
public class TextQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_quiz_id")
    private int textQuizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String question;

    private String hint;


}

//private int memberInfoId;

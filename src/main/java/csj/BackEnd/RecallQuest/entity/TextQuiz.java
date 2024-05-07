package csj.BackEnd.RecallQuest.entity;

import jakarta.persistence.*; //모든 클래스와 인터페이스를 가져와서 사용. TextQuiz테이블 불러옴.
import lombok.Data;  // getter, setter, equals, hashCode, toString
import lombok.Getter;
import lombok.Setter;
//import java.util.ArrayList;
//import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "textquiz")
public class TextQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_quiz_id")
    private int textQuizId;

    private int memberInfoId;

    private String question;

    private String hint;

}

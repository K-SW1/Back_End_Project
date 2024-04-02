package csj.BackEnd.RecallQuest.domain;

import jakarta.persistence.*; //모든 클래스와 인터페이스를 가져와서 사용. TextQuiz테이블 불러옴.
import lombok.Data; // getter, setter, equals, hashCode, toString
import lombok.Getter;
import lombok.Setter;
//import java.util.ArrayList;
//import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "textchoice")
public class TextChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_choice_id")
    private int textChoiceId;

    @ManyToOne
    @JoinColumn(name = "text_quiz_id")
    private TextQuiz textQuiz;

    @Column(name = "choice_text")
    private String choiceText;

    private boolean answer;

    // 생성자 추가
    public TextChoice(String choiceText, boolean answer) {
        this.choiceText = choiceText;
        this.answer = answer;
    }


    // 기본 생성자 추가 (JPA 사용 시 필요) 객채 필드로 초기화 해줌.
    public TextChoice() {}
}

package csj.BackEnd.RecallQuest.entity;

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
@Table(name = "textdistractor")
public class TextDistractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_distractor_id")
    private int textDistractorId;

    @ManyToOne
    @JoinColumn(name = "text_quiz_id")
    private TextQuiz textQuiz;

    @Column(name = "textzQuiz_Distractor")
    private String textzQuizDistractor;

    private boolean validation;

    // 생성자 추가
    public TextDistractor(String textzQuizDistractor, boolean validation) {
        this.textzQuizDistractor = textzQuizDistractor;
        this.validation = validation;
    }


    // 기본 생성자 추가 (JPA 사용 시 필요) 객채 필드로 초기화 해줌.
    public TextDistractor() {}
}

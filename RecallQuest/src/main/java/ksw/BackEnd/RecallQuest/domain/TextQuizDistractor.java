package ksw.BackEnd.RecallQuest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class TextQuizDistractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_quiz_distractor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "text_quiz_id")
    private TextQuiz textQuiz;

    private String textQuizDistractor;

    private boolean validation;

}

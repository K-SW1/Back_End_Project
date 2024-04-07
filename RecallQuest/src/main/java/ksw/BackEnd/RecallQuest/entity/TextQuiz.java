package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class TextQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_quiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String question;

    private String hint;

    @OneToMany(mappedBy = "textQuiz", cascade = CascadeType.ALL)
    private List<TextQuizDistractor> textQuizDistractors = new ArrayList<>();
}

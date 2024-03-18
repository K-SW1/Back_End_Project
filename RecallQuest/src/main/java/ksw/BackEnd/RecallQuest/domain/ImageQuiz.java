package ksw.BackEnd.RecallQuest.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image_quiz")
@Entity
public class ImageQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_quiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String question;

    private String hint;

    @OneToMany(mappedBy = "imageQuiz", cascade = CascadeType.ALL) //문제에 대한 이미지
    private List<QuestionImage> questionImages = new ArrayList<>();

    @OneToMany(mappedBy = "imageQuiz", cascade = CascadeType.ALL)
    private List<ImageQuizDistractor> imageQuizDistractors = new ArrayList<>();

    @Builder
    public ImageQuiz(String question, String hint, List<QuestionImage> questionImages) {
        this.question = question;
        this.hint = hint;
        this.questionImages = questionImages;
    }

}

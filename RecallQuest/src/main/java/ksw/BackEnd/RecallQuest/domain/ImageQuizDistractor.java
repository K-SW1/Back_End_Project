package ksw.BackEnd.RecallQuest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class ImageQuizDistractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_quiz_distractor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_quiz_id")
    private ImageQuiz imageQuiz;

    private String imageQuizDistractor;

    private boolean validation;

    @OneToMany(mappedBy = "imageQuizDistractor", cascade = CascadeType.ALL)
    private List<DistractorImage> distractorImages = new ArrayList<>();
}

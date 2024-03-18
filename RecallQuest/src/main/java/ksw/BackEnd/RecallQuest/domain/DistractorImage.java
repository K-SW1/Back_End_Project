package ksw.BackEnd.RecallQuest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class DistractorImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distractor_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_quiz_distractor_id")
    private ImageQuizDistractor imageQuizDistractor;

    private String imagePath;
}

package ksw.BackEnd.RecallQuest.domain;

import jakarta.persistence.*;
import ksw.BackEnd.RecallQuest.imagequiz.ImageQuizDistractorRequestDto;
import ksw.BackEnd.RecallQuest.imagequiz.ImageQuizRequestDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "image_quiz_distractor")
@AllArgsConstructor
@Entity
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

    @Builder
    public ImageQuizDistractor(String imageQuizDistractor, boolean validation) {
        this.imageQuizDistractor = imageQuizDistractor;
        this.validation = validation;
    }

    public void changeInfo (ImageQuizDistractorRequestDto imageQuizDistractorRequestDto) {
        this.imageQuizDistractor = imageQuizDistractorRequestDto.getImageQuizDistractor();
        this.validation = imageQuizDistractorRequestDto.isValidation();
    }

}

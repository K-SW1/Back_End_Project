package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.ImageQuizDistractorRequestDto;
import ksw.BackEnd.RecallQuest.imagequizdistractor.dto.UpdateRequestDto;
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
    @Column(name = "image_quiz_distractor_seq")
    private Long imageQuizDistractorSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_quiz_seq")
    private ImageQuiz imageQuiz;

    private String imageQuizDistractor;

    private boolean validation;

    @OneToMany(
            mappedBy = "imageQuizDistractor",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<DistractorImage> distractorImages = new ArrayList<>();

    @Builder
    public ImageQuizDistractor(String imageQuizDistractor, boolean validation, ImageQuiz imageQuiz) {
        this.imageQuizDistractor = imageQuizDistractor;
        this.validation = validation;
        this.imageQuiz = imageQuiz;
    }

    public void changeInfo (UpdateRequestDto updateRequestDto) {
        this.imageQuizDistractor = updateRequestDto.getRevisedDistractor();
        this.validation = updateRequestDto.isValidation();
    }

    public void addImage (DistractorImage distractorImage) {
        this.distractorImages.add(distractorImage);
    }

}

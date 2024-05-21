package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*;
import ksw.BackEnd.RecallQuest.imagequiz.dto.ImageQuizRequestDto;
import ksw.BackEnd.RecallQuest.imagequiz.dto.UpdateRequestDto;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "image_quiz")
@AllArgsConstructor
@Entity
public class ImageQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_quiz_seq")
    private Long imageQuizSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    private String question;

    private String hint;

    @OneToMany(
            mappedBy = "imageQuiz",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    ) //문제에 대한 이미지
    private List<QuestionImage> questionImages = new ArrayList<>();

    @OneToMany(
            mappedBy = "imageQuiz",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<ImageQuizDistractor> imageQuizDistractors = new ArrayList<>();


    @Builder
    public ImageQuiz(String question, String hint, Member member) {
        this.question = question;
        this.hint = hint;
        this.member = member;
    }

    public void changeInfo (UpdateRequestDto updateRequestDto) {
        this.question = updateRequestDto.getRevisedQuestion();
        this.hint =  updateRequestDto.getHint();
    }

    public void addImage (QuestionImage questionImage) {
        this.questionImages.add(questionImage);
    }

//    public static ImageQuiz createdImageQuiz(Member member, OrderItem orderItem){
//        ImageQuiz imageQuiz = new ImageQuiz();
//        imageQuiz.setMember(member);
//        order.addOrderItem(orderItem);
//        return order;
//    }


}

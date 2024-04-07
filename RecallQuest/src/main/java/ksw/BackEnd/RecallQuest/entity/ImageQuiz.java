package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*;
import ksw.BackEnd.RecallQuest.imagequiz.dto.ImageQuizRequestDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "image_quiz")
@AllArgsConstructor
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
    public ImageQuiz(String question, String hint, Member member) {
        this.question = question;
        this.hint = hint;
        this.member = member;
    }

    public void changeInfo (ImageQuizRequestDto imageQuizRequestDto) {
        this.question = imageQuizRequestDto.getQuestion();
        this.hint =  imageQuizRequestDto.getHint();
    }

//    public static ImageQuiz createdImageQuiz(Member member, OrderItem orderItem){
//        ImageQuiz imageQuiz = new ImageQuiz();
//        imageQuiz.setMember(member);
//        order.addOrderItem(orderItem);
//        return order;
//    }


}

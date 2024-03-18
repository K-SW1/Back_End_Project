package ksw.BackEnd.RecallQuest.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_image")
@Entity
public class QuestionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_quiz_id") //이미지 퀴즈 문제
    private ImageQuiz imageQuiz;

//    private String imagePath;

    private String originFilename; //원본 이름
    private String storeFilename; //파일을 저장한 이름, 원본 이름에서 중복이 날 수 있기 때문에 생성

    @Builder
    public QuestionImage(String originFilename, String storeFilename) { //이게 맞나..?ㅜ
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
    }
}

package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_image")
@Entity
public class QuestionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_image_Seq")
    private Long questionImageSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_quiz_seq") //이미지 퀴즈 문제
    private ImageQuiz imageQuiz;

//    private String imagePath;

    private String originFilename; //원본 이름
    private String storeFilename; //파일을 저장한 이름, 원본 이름에서 중복이 날 수 있기 때문에 생성
    private String type; //타입
    private String filePath; //경로

    @Builder
    public QuestionImage(String originFilename, String storeFilename, String type, String filePath, ImageQuiz imageQuiz) { //이게 맞나..?ㅜ
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
        this.type = type;
        this.filePath = filePath;
        this.imageQuiz = imageQuiz;
    }

//    public void changeInfo (MultipartFile multipartFile, String filePath, String storeFilename) {
//        this.originFilename = multipartFile.getOriginalFilename();
//        this.storeFilename = storeFilename;
//        this.type = multipartFile.getContentType();
//        this.filePath = filePath;
//        this.imageQuiz = imageQuiz;
//    }
}

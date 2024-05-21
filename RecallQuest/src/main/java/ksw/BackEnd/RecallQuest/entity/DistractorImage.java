package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "distractor_image")
@Entity
public class DistractorImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distractor_image_seq")
    private Long distractorImageSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_quiz_distractor_seq")
    private ImageQuizDistractor imageQuizDistractor;

    private String originFilename; //원본 이름
    private String storeFilename; //파일을 저장한 이름, 원본 이름에서 중복이 날 수 있기 때문에 생성
    private String type; //타입
    private String filePath; //경로

    @Builder
    public DistractorImage(String originFilename, String storeFilename, String type, String filePath, ImageQuizDistractor imageQuizDistractor) { //이게 맞나..?ㅜ
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
        this.type = type;
        this.filePath = filePath;
        this.imageQuizDistractor = imageQuizDistractor;
    }
}

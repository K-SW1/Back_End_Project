package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*; // 모든 클래스와 인터페이스를 가져와서 사용
import lombok.*; // Lombok 관련 import

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor // Lombok가 모든 필드를 포함하는 생성자를 생성합니다
@Builder
@Table(name = "image_distractor") // 테이블 이름 설정
public class ImagequizDistractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_distractor_id")
    private int imageDistractorId; // ID 필드

    @ManyToOne
    @JoinColumn(name = "image_quiz_id") // ImageQuiz와 연결
    private ImageQuiz imageQuiz;

    @Column(name = "imageDistractor") // 선택지를 위한 필드
    private String imageDistractor; // 이미지 선택지

    private boolean validation; // 정답 여부를 나타내는 필드

    // 생성자 추가
    public ImagequizDistractor(String imageDistractor, boolean validation) {
        this.imageDistractor = imageDistractor;
        this.validation = validation;
    }

    // 기본 생성자 추가 (JPA 사용 시 필요)
    public ImagequizDistractor() {}
}

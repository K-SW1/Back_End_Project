package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*; // 모든 클래스와 인터페이스를 가져와서 사용
import lombok.*; // Lombok 관련 import
import lombok.Data;  // getter, setter, equals, hashCode, toString
import java.util.List;


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor // Lombok가 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor // Lombok가 모든 필드를 포함하는 생성자를 생성합니다
@Builder
@Table(name = "imagequiz") // 테이블 이름 설정
public class ImageQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_quiz_id")
    private int imageQuizId; // 이미지 퀴즈 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member; // 사용자와의 관계

    private String question; // 질문

    private String hint; // 힌트

    private String imageUrl; // 이미지 URL 추가

    // OneToMany 관계 추가
    @OneToMany(mappedBy = "imageQuiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagequizDistractor> distractors; // 선택지 리스트

    // distractors getter 추가
    public List<ImagequizDistractor> getDistractors() {
        return distractors;
    }

    public void setDistractors(List<ImagequizDistractor> distractors) {
        this.distractors = distractors;
    }
}

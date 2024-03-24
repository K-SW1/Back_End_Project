package ksw.BackEnd.RecallQuest.kkk;

import jakarta.persistence.*;
import ksw.BackEnd.RecallQuest.domain.ImageQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FILE_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id") //이미지 퀴즈 문제
    private Quiz quiz;
}
package ksw.BackEnd.RecallQuest.kkk;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "QUIZ")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    private String question;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL) //문제에 대한 이미지
    private List<FileData> fileData = new ArrayList<>();

    @Builder
    public Quiz(String question){
        this.question = question;
    }


}

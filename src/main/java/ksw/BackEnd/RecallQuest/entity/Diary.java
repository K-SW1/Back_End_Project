package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;  // getter, setter, equals, hashCode, toString

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor // Lombok가 매개변수가 없는 기본 생성자를 생성합니다
@AllArgsConstructor // Lombok가 모든 필드를 포함하는 생성자를 생성합니다
@Builder
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private int diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //환자이름
    private String patient;
    //구분
    private String division;
    //시간
    private String time;
    //메모내용
    private String memo;
    //등록날짜
    private String date;

}

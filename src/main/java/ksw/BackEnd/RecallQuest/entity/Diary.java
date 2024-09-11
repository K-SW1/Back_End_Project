package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;  // getter, setter, equals, hashCode, toString

import java.time.LocalDate;


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
    @JoinColumn(name = "member_seq")
    private Member member;

    private String name;

    private String time;

    private String memo;

    private LocalDate date;


}

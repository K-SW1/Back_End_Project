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
@Table(name = "missing")
public class Missing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "missing_id")
    private int missingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    private String age;

    private String date;

    private String area;

    private String gender;

    private String img;


}

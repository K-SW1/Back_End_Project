package ksw.BackEnd.RecallQuest.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login")
@Entity
public class Login {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_seq")
    private Long id;

    @Column(name = "user_login_id")
    private String userLoginId; //로그인 아이디

    @Column(name = "user_login_password")
    private String userLoginPassword; //로그인 비밀번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @Builder Login(String userLoginId, String userLoginPassword, Member member) {
        this.userLoginId = userLoginId;
        this.userLoginPassword = userLoginPassword;
        this.member = member;
    }
}

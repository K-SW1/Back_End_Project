package ksw.BackEnd.RecallQuest.entity;

import jakarta.persistence.*;
import ksw.BackEnd.RecallQuest.member.dto.MemberSaveRequestDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/*
setter를 사용하면 객체를 언제든지 변경할 수 있는 가능성이 생기고, 어디서 누구에 의해서 변경됐는지 추적하기 힘들기 떄문에 사용하면 안됨
그렇기 때문에 builder를 사용함, 객체 생성시 초기화할때 최초로 값을 넣고 변경을 하지 않음!
 */

@Getter
@NoArgsConstructor //기본 생성자, 엔티티는 기본 생성자 필수임, 왜 protected로 레벨을 지정하지 않으셨을까?
@AllArgsConstructor //모든 필드를 인자로 하는 생성자 만들기, 이걸 사용하신 이유 궁금?
@Table(name = "member")
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long memberSeq;

    @Column(name = "member_name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "member_mail")
    private String mail;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Login login;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TextQuiz> textQuizzes = new ArrayList<>();

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<ImageQuiz> imageQuizzes = new ArrayList<>();

    @Builder
    public Member(String name, String phoneNumber, String mail) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public void changeInfo (MemberSaveRequestDto memberSaveRequestDto) {
        this.phoneNumber = memberSaveRequestDto.getPhoneNumber();
        this.mail = memberSaveRequestDto.getMail();;
    }

}

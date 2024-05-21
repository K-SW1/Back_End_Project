package ksw.BackEnd.RecallQuest.member.repository;

import ksw.BackEnd.RecallQuest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByName(String name);

    @Query("SELECT m FROM Member m JOIN m.login l WHERE l.userLoginId = :userLoginId")
    Member findByUserLoginId(@Param("userLoginId") String userLoginId);

    void deleteById(Long memberSeq);

    Boolean existsByMail(String mail);
}

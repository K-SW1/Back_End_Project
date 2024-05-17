package csj.BackEnd.RecallQuest.member.repository;

import csj.BackEnd.RecallQuest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByName(String name);

    void deleteById(Long memberSeq);
}

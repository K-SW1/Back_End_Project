package ksw.BackEnd.RecallQuest.repository;

import jakarta.persistence.EntityManager;
import ksw.BackEnd.RecallQuest.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByName(String name);

    void deleteById(Long memberSeq);
}

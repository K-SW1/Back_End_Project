package csj.BackEnd.RecallQuest.member.repository;

import csj.BackEnd.RecallQuest.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByUserLoginId(String loginId);

//    Login findById(long loginId);
}

package ksw.BackEnd.RecallQuest.jwt.repository;

import jakarta.transaction.Transactional;
import ksw.BackEnd.RecallQuest.jwt.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    Boolean existsByRefresh(String refresh);

    @Transactional //deleteBy의 경우 선언해줘야 함
    void deleteByRefresh(String refresh);
}

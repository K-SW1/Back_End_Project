package ksw.BackEnd.RecallQuest.dao;

import ksw.BackEnd.RecallQuest.DataNotFoundException;
import ksw.BackEnd.RecallQuest.domain.Login;
import ksw.BackEnd.RecallQuest.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaLoginDao implements LoginDao{

    private final LoginRepository loginRepository;

    @Override
    public Login save(Login login) {
        return loginRepository.save(login);
    }

    @Override
    public Login findByUserLoginId(String userLoginId) {
        return loginRepository.findByUserLoginId(userLoginId).orElseThrow(() -> new DataNotFoundException("아이디를 찾을 수 없습니다."));
    }
}

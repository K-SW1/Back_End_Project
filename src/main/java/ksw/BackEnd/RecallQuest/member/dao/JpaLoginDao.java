package ksw.BackEnd.RecallQuest.member.dao;

import ksw.BackEnd.RecallQuest.DataNotFoundException;
import ksw.BackEnd.RecallQuest.common.Exception.login.LoginIdNotFoundException;
import ksw.BackEnd.RecallQuest.entity.Login;
import ksw.BackEnd.RecallQuest.member.repository.LoginRepository;
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
        return loginRepository.findByUserLoginId(userLoginId).orElseThrow(() -> new LoginIdNotFoundException("존재하지 않는 로그인 아이디입니다."));
    }

    @Override
    public Boolean existsByUserLoginId(String loginId) {
        return loginRepository.existsByUserLoginId(loginId);
    }
}

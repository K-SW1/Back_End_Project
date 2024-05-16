package csj.BackEnd.RecallQuest.member.dao;

import csj.BackEnd.RecallQuest.DataNotFoundException;
import csj.BackEnd.RecallQuest.entity.Login;
import csj.BackEnd.RecallQuest.member.repository.LoginRepository;
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
        return loginRepository.findByUserLoginId(userLoginId).orElseThrow(() -> new DataNotFoundException("입력하신 아이디를 찾을 수 없습니다."));
    }
}

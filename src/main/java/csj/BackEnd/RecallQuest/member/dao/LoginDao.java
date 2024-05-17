package csj.BackEnd.RecallQuest.member.dao;

import csj.BackEnd.RecallQuest.entity.Login;

public interface LoginDao {

    Login save(Login login);

    Login findByUserLoginId(String userLoginId);
}

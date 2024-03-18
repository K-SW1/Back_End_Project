package ksw.BackEnd.RecallQuest.dao;

import ksw.BackEnd.RecallQuest.domain.Login;
import ksw.BackEnd.RecallQuest.domain.Member;

public interface LoginDao {

    Login save(Login login);

    Login findByUserLoginId(String userLoginId);
}

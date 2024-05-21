package ksw.BackEnd.RecallQuest.member.dao;

import ksw.BackEnd.RecallQuest.entity.Login;

public interface LoginDao {

    Login save(Login login);

    Login findByUserLoginId(String userLoginId);

    Boolean existsByUserLoginId(String loginId);
}

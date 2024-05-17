package csj.BackEnd.RecallQuest.jwt.service;

import csj.BackEnd.RecallQuest.entity.Login;
import csj.BackEnd.RecallQuest.jwt.dto.CustomUserDetails;
import csj.BackEnd.RecallQuest.member.dao.LoginDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginDao loginDao;

    public CustomUserDetailsService(LoginDao loginDao) {

        this.loginDao = loginDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Login userData = loginDao.findByUserLoginId(username); //로그인 아이디로 로그인 엔티티 가져오기

        if (userData != null) {

            return new CustomUserDetails(userData); //저 dto에 넣어서 어센틱 메니저에 전달
        }


        return null;
    }
}

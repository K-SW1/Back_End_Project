package ksw.BackEnd.RecallQuest.jwt.dto;

import ksw.BackEnd.RecallQuest.entity.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails  implements UserDetails { //DTO임

    private final Login userEntity;

    public CustomUserDetails(Login userEntity) {

        this.userEntity = userEntity;
    }

    //계정의 역할을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    //비밀번호 가져오기
    @Override
    public String getPassword() {

        return userEntity.getUserLoginPassword();
    }

    //이름 가져오기(로그인 아이디)
    @Override
    public String getUsername() {

        return userEntity.getUserLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}

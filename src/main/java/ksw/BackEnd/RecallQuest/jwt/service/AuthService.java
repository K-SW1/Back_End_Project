package ksw.BackEnd.RecallQuest.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import ksw.BackEnd.RecallQuest.jwt.JWTUtil;
import ksw.BackEnd.RecallQuest.jwt.dto.LoginDto;
import ksw.BackEnd.RecallQuest.jwt.dto.TokenInfo;
import ksw.BackEnd.RecallQuest.jwt.entity.RefreshEntity;
import ksw.BackEnd.RecallQuest.jwt.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public TokenInfo authenticate(LoginDto loginDTO) throws AuthenticationException, IOException {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();


        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 유저 정보
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        // Refresh 토큰 저장
        addRefreshEntity(username, refresh, 86400000L);

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(access)
                .refreshToken(refresh)
                .build();
    }

    // 로그아웃 처리 로직
    public void logout(String refreshToken) {
        if (refreshToken == null) {
            throw new IllegalArgumentException("Refresh token is null");
        }

        // 토큰 만료 여부 확인
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        // 토큰 유형 확인
        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // DB에서 토큰 존재 여부 확인
        Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
        if (!isExist) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // Refresh 토큰 삭제
        refreshRepository.deleteByRefresh(refreshToken);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}

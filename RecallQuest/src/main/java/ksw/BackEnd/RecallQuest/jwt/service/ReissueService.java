package ksw.BackEnd.RecallQuest.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import ksw.BackEnd.RecallQuest.jwt.JWTUtil;
import ksw.BackEnd.RecallQuest.jwt.dto.TokenInfo;
import ksw.BackEnd.RecallQuest.jwt.entity.RefreshEntity;
import ksw.BackEnd.RecallQuest.jwt.exception.IllegalAccessTokenException;
import ksw.BackEnd.RecallQuest.jwt.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public TokenInfo tokenReissue(String refreshToken) {

        String refresh = refreshToken;

        if (refresh == null) {

            //response status code
            throw new IllegalAccessTokenException("토큰 요청 정보가 올바르지 않습니다.", 402);

        }

        //expired check, 리프레시 토큰 재발급은 엑세스는 만료되고, 리프레시는 만료되지 않은 상태에서 가능함
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            throw new IllegalAccessTokenException("만료된 토큰 입니다", 402);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            throw new IllegalAccessTokenException("토큰 요청 정보가 올바르지 않습니다.", 402);
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {

            //response body
            throw new IllegalAccessTokenException("토큰 요청 정보가 올바르지 않습니다.", 402);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);


        TokenInfo tokenInfo = TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(newAccess)
                .refreshToken(newRefresh)
                .build();

        return tokenInfo;
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

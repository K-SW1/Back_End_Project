package ksw.BackEnd.RecallQuest.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
//JWT 발급, 검증 클래스
@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {

        //우리가 지정해둔 키를 객체 변수로 암호화
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    //토큰 판단용 메서드
    public String getCategory(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public String getUsername(String token) {

        //토큰이 우리 서버에서 생성되었는지, 우리 서버에서 생성된 것이 우리가 가지고 있는 키랑 맞는지 확인
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        //토큰 만료 시간이 현재 시간보다 이전인지 확인
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    //로그인이 성공했을때 실행하는 토큰 생성 메서드
    public String createJwt(String category, String username, String role, Long expiredMs) {

        return Jwts.builder() //토큰 payload에 저장될 정보, username, role, 생성일, 만료일
                .claim("category", category)
                .claim("username", username)
                .claim("role", role) //토큰 내부에 payload에 해당하는 부분에 넣는 데이터
                .issuedAt(new Date(System.currentTimeMillis())) //현재 발행 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) //토큰 소멸 시간
                .signWith(secretKey) //시그니처 생성
                .compact();
    }
}

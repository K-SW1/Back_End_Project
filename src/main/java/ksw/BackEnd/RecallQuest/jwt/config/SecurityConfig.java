package ksw.BackEnd.RecallQuest.jwt.config;

import jakarta.servlet.http.HttpServletRequest;
import ksw.BackEnd.RecallQuest.jwt.JWTFilter;
import ksw.BackEnd.RecallQuest.jwt.JWTUtil;
import ksw.BackEnd.RecallQuest.jwt.repository.RefreshRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity //시큐리티를 위한 컨피그
public class SecurityConfig {


    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    private final RefreshRepository refreshRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, RefreshRepository refreshRepository) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean //비밀번호를 해시로 암호화할때 사용하는 것임
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource(){

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8088"));
//                        configuration.setAllowedOrigins(Collections.singletonList("http://192.210.55.10:8088"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));

        //csrf disable, 세션방식에서는 세션이 항상 고정되기 때문에, csrf공격을 필수적으로 방어해야함, jwt는 세션을 stateless로 관리하기 때문에
        //csrf공격을 방어하지 않아도 됨 -> 세션을 stateless로 관리 = 서버가 클라이언트의 상태(사용자 인증 상태, 세션 정보)를 서버쪽에서 저장하지 않는 것
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());


        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/login","/api/logout", "/", "/members/join").permitAll() //모든 사용자 접근 가능
                        .requestMatchers("/admin").hasRole("ADMIN") //어드민만 접근 가능
                        .requestMatchers("/aa").hasRole("USER")
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/members/**").permitAll()
                        .requestMatchers("/imagequiz/**").permitAll()
                        .requestMatchers("/imagequizDistractor/**").permitAll()
                        .requestMatchers("/textquiz/**").permitAll()
                        .requestMatchers("/textquizDistractor/**").permitAll()
                        .requestMatchers("/reissue").permitAll()
                        .requestMatchers("/missing/**").permitAll()
                        .requestMatchers("/diary/**").permitAll()
                        .anyRequest().authenticated()); //나머지는 로그인한 사용자만 접근 가능

//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .anyRequest().permitAll() // 모든 요청에 대해 접근 허용
//                );

        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //세션 설정, 세션을 stateless로 설정, 제일 중요함!!!
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

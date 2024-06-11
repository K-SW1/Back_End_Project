package ksw.BackEnd.RecallQuest.jwt.controller;

import ksw.BackEnd.RecallQuest.jwt.dto.LoginDto;
import ksw.BackEnd.RecallQuest.jwt.dto.TokenInfo;
import ksw.BackEnd.RecallQuest.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDTO) throws IOException {
        TokenInfo tokenInfo = authService.authenticate(loginDTO);
        return ResponseEntity.ok(tokenInfo);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@RequestHeader("refreshToken") String refreshToken) {
        try {
            authService.logout(refreshToken);
            return ResponseEntity.ok("Logout successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

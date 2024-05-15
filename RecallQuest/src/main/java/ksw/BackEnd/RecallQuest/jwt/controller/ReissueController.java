package ksw.BackEnd.RecallQuest.jwt.controller;

import ksw.BackEnd.RecallQuest.jwt.dto.TokenInfo;
import ksw.BackEnd.RecallQuest.jwt.service.ReissueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping("/reissue")
    public TokenInfo reissue(@RequestHeader("refreshToken") String refreshToken) {

        String refresh = refreshToken;
        TokenInfo tokenInfo = reissueService.tokenReissue(refresh);
        return tokenInfo;
    }

}
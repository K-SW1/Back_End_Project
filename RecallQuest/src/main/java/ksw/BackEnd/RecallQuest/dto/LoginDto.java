package ksw.BackEnd.RecallQuest.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

    private String userLoginId;

    private String userLoginPassword;
}

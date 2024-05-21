package ksw.BackEnd.RecallQuest.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    private String username;
    private String password;
}
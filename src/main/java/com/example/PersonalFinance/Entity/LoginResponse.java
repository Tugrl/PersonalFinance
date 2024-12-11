package com.example.PersonalFinance.Entity;

import com.example.PersonalFinance.Dto.UserDTO;

public class LoginResponse {
    private String jwt;
    private UserDTO user;

    public LoginResponse(String jwt, UserDTO user) {
        this.jwt = jwt;
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}


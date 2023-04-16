package com.project.vinylata.Security.jwt;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {
    private String email;
    private String password;
}

package com.estatisticasSpotify.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpHeaders;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {
    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    @GetMapping("/login")
    public String getUrlAutenticacao() {
        String scope = "user-read-private user-read-email user-top-read";
        return "https://accounts.spotify.com/authorize" +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&scope=" + scope.replace(" ", "%20") +
                "&redirect_uri=" + redirectUri;
    }
}


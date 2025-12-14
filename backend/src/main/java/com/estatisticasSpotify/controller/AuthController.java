package com.estatisticasSpotify.controller;

import com.estatisticasSpotify.External.SpotifyService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.Map;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    @GetMapping("/login")
    public Map<String, String> getUrlAutenticacao() {
        String url = SpotifyService.getSpotifyLoginUrl(clientId, redirectUri);

        return Map.of("url", url);
    }

    @GetMapping("/callback")
    public void callback(@RequestParam("code") String codigo, @RequestParam(name = "state", required = false) String stateRecebido) {
        if (!SpotifyService.getState().equals(stateRecebido)) {
            return;
        }

        HttpResponse<String> responseApiToken = SpotifyService.getSpotifyToken(codigo, clientId, clientSecret, redirectUri);

        if (responseApiToken == null || responseApiToken.statusCode() != 200) {
            return;
        }

        String token = getTokenDaResponse(responseApiToken.body());
        System.out.println("TOKEN: " + token);

    }


    private String getTokenDaResponse(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }
}


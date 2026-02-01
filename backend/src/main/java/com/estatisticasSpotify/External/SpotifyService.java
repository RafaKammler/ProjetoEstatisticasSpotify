package com.estatisticasSpotify.External;

import com.estatisticasSpotify.model.enums.EndpointsSpotifyApiEnum;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SpotifyService {

    private static final String state = "34fFs29kd09";

    public static String getSpotifyLoginUrl(String clientId, String redirectUri) {
        String scope = "user-read-private user-read-email user-top-read";
        String url = "https://accounts.spotify.com/authorize" +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&scope=" + scope.replace(" ", "%20") +
                "&redirect_uri=" + redirectUri +
                "&show_dialog=true" +
                "&state=" + state;
        return url;
    }

    public static String getUsuarioSpotifyUrl() {
        return "https://api.spotify.com/v1/me";
    }


    public static HttpResponse<String> getSpotifyToken(String codigo, String clientId, String clientSecret, String redirectUri) {
        String url = EndpointsSpotifyApiEnum.BASE_URL_TOKEN.getUrl();
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        String authorizationHeader = "Basic " + encodedCredentials;
        String form = "code=" + URLEncoder.encode(codigo, StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&grant_type=authorization_code";

        try {
            HttpClient client = HttpClient.newBuilder().build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", authorizationHeader)
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
public static HttpResponse<String> getInformacoesUsuario(String token) {
        String url = getUsuarioSpotifyUrl();

        try {
            HttpClient client = HttpClient.newBuilder().build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getState() {
        return state;
    }
}

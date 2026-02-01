package com.estatisticasSpotify.controller;

import com.estatisticasSpotify.External.SpotifyService;
import com.estatisticasSpotify.model.dominio.Usuario;
import com.estatisticasSpotify.service.UsuariosService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Map;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuariosService usuariosService;

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

        HttpResponse<String> responseUsuario = SpotifyService.getInformacoesUsuario(getCampoJson(responseApiToken.body(), "access_token"));

        if(responseUsuario == null || responseUsuario.statusCode() != 200){
            return;
        }

        Usuario usuario =  getUsuarioLogado(responseApiToken.body(), responseUsuario.body());
        usuariosService.salvarUsuario(usuario);
    }

    private Usuario getUsuarioLogado(String responseTokenBody, String responsePerfilBody) {
        String codUsuario = getCampoJson(responsePerfilBody, "id");

        Usuario usuario = usuariosService.buscarPorId(codUsuario)
                .orElse(new Usuario());

        usuario.setUsuCod(codUsuario);
        usuario.setUsuNome(getCampoJson(responsePerfilBody, "display_name"));
        usuario.setUsuEmail(getCampoJson(responsePerfilBody, "email"));
        usuario.setUsuNacionalidade(getCampoJson(responsePerfilBody, "country"));

        usuario.setUsuAcessToken(getCampoJson(responseTokenBody, "access_token"));

        String refreshToken = getCampoJson(responseTokenBody, "refresh_token");
        if (refreshToken != null) {
            usuario.setUsuRefreshToken(refreshToken);
        }

        long expiresIn = Long.parseLong(getCampoJson(responseTokenBody, "expires_in"));
        usuario.setUsuTokenExpiration(Instant.now().plusSeconds(expiresIn));

        return usuario;
    }

    private String getCampoJson(String json, String campo) {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            if (jsonObject.has(campo) && !jsonObject.get(campo).isJsonNull()) {
                return jsonObject.get(campo).getAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


package com.estatisticasSpotify.model.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "USUARIO") // Ajustado para plural
public class Usuario {
    @Id
    @Column(name = "USU_COD")
    private String usuCod;
    @Column(name = "USU_NOME")
    private String usuNome;
    @Column(name = "USU_EMAIL")
    private String usuEmail;
    @Column(name = "USU_NACIONALIDADE", length = 2)
    private String usuNacionalidade;
    @Column(name = "USU_ACESS_TOKEN", columnDefinition = "TEXT")
    private String usuAcessToken;
    @Column(name = "USU_REFRESH_TOKEN", columnDefinition = "TEXT")
    private String usuRefreshToken;
    @Column(name = "USU_TOKEN_EXPIRATION")
    private Instant usuTokenExpiration;

    public Usuario() {
    }

    public Usuario(String usuCod) {
        this.usuCod = usuCod;
    }

    public String getUsuCod() {
        return usuCod;
    }

    public void setUsuCod(String usuCod) {
        this.usuCod = usuCod;
    }

    public String getUsuNome() {
        return usuNome;
    }

    public void setUsuNome(String usuNome) {
        this.usuNome = usuNome;
    }

    public String getUsuEmail() {
        return usuEmail;
    }

    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }

    public String getUsuNacionalidade() {
        return usuNacionalidade;
    }

    public void setUsuNacionalidade(String usuNacionalidade) {
        this.usuNacionalidade = usuNacionalidade;
    }

    public String getUsuAcessToken() {
        return usuAcessToken;
    }

    public void setUsuAcessToken(String usuAcessToken) {
        this.usuAcessToken = usuAcessToken;
    }

    public String getUsuRefreshToken() {
        return usuRefreshToken;
    }

    public void setUsuRefreshToken(String usuRefreshToken) {
        this.usuRefreshToken = usuRefreshToken;
    }

    public Instant getUsuTokenExpiration() {
        return usuTokenExpiration;
    }

    public void setUsuTokenExpiration(Instant usuTokenExpiration) {
        this.usuTokenExpiration = usuTokenExpiration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuarios = (Usuario) o;
        return Objects.equals(usuCod, usuarios.usuCod);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(usuCod);
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "usuCod='" + usuCod + '\'' +
                ", usuNome='" + usuNome + '\'' +
                '}';
    }
}
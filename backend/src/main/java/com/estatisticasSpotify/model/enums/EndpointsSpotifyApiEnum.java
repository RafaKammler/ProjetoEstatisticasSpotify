package com.estatisticasSpotify.model.enums;

public enum EndpointsSpotifyApiEnum {
    BASE_URL_TOKEN() {
        public String getUrl() {
            return "https://accounts.spotify.com/api/token";
        }
    },
    BASE_URL_API() {
        public String getUrl() {
            return "https://api.spotify.com/v1";
        }
    };

    public abstract String getUrl();
}

import React from 'react';
import '../styles/Login.css';
import { FaSpotify } from "react-icons/fa";

function Login() {
    return (
        <div className="login-page">
            <div className="login-container">
                <img
                    src="https://upload.wikimedia.org/wikipedia/commons/1/19/Spotify_logo_without_text.svg"
                    alt="Spotify Logo"
                    className="spotify-logo"
                />
                <h1 className="login-title">Suas estatísticas em um só lugar</h1>
                <p className="login-description">
                    Conecte-se para descobrir seus artistas, gêneros e músicas mais ouvidos.
                </p>
                <button className="spotify-login-btn">
                    <FaSpotify size={22} />
                    <span>Entrar com Spotify</span>
                </button>
            </div>
        </div>
    );
}

export default Login;

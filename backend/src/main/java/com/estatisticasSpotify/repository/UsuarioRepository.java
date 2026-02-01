package com.estatisticasSpotify.repository;

import com.estatisticasSpotify.model.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}

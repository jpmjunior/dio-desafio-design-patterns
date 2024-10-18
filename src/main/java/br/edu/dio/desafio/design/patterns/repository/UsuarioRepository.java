package br.edu.dio.desafio.design.patterns.repository;

import br.edu.dio.desafio.design.patterns.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("""
            SELECT u FROM Usuario u
            JOIN FETCH u.autenticacao
            JOIN FETCH u.autenticacao.roles
            WHERE u.autenticacao.username = :username
            """)
    Optional<Usuario> findByUsername(String username);

}

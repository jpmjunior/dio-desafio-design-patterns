package br.edu.dio.desafio.design.patterns.repository;

import br.edu.dio.desafio.design.patterns.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u JOIN FETCH u.auth JOIN FETCH u.auth.roles WHERE u.auth.username = (:username)")
    Usuario findByUsername(@Param("username") String username);

}

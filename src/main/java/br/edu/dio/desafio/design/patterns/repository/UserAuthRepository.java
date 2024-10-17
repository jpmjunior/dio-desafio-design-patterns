package br.edu.dio.desafio.design.patterns.repository;

import br.edu.dio.desafio.design.patterns.model.UserAuth;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {

    @Query("SELECT u FROM UserAuth u JOIN FETCH u.roles WHERE u.username = (:username)")
    UserAuth findByUsername(@Param("username") String username);

}

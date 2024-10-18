package br.edu.dio.desafio.design.patterns.repository;

import br.edu.dio.desafio.design.patterns.model.Autenticacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutenticacaoRepository extends JpaRepository<Autenticacao, Integer> {

    Optional<Autenticacao> findByUsername(String username);

}

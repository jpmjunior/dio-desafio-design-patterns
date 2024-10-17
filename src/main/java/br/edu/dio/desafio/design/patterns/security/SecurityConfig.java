package br.edu.dio.desafio.design.patterns.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SecurityConfig {

    @Value("${security.config.prefix}")
    private String prefix;

    @Value("${security.config.key}")
    private String key;

    @Value("${security.config.expiration}")
    private Long expiration;

}
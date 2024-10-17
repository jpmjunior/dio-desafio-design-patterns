package br.edu.dio.desafio.design.patterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DioCursoSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DioCursoSpringSecurityApplication.class, args);
	}

}

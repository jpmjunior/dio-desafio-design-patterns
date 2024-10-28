package br.edu.dio.desafio.design.patterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DioDesafioDesignPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DioDesafioDesignPatternsApplication.class, args);
	}

}

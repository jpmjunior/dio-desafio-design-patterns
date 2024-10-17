# Desafio de Projeto
## Design Pattern com Java
#### Professor: [Venilton FalvoJr](https://github.com/falvojr)
#### Instituição: [DIO](https://dio.me)
#### Bootcamp: [Claro - Java com Spring Boot](https://web.dio.me/track/coding-the-future-claro-java-spring-boot)

## Descrição do Desafio
Agora é a sua hora de brilhar! Crie uma solução que explore o conceito de Padrões de Projeto na pŕatica. Para isso, você pode reproduzir um dos projetos que criamos durante as aulas ou, caso se sinta preparado, desenvolver uma nova ideia do zero ;-) 

Dica: Além dos projetos/repositórios que criamos para este desafio, caso queira explorar novos padrões de projeto digite no Google: “java design patterns github” ou “java design patterns examples”. Com isso, você conhecerá novos padrões e implementações de referência que podem ajudá-lo a dominar esse tema!

## Implementações de referência e slides
#### [GitHub com Padrões de Projeto usando Java Puro](https://github.com/digitalinnovationone/lab-padroes-projeto-java)
#### [GitHub com Padrões de Projeto usando Spring](https://github.com/digitalinnovationone/lab-padroes-projeto-spring)
#### [Slides](https://docs.google.com/presentation/d/1WU8gLHbB1s9XCIGsQ87gD36kt398qLch/edit?usp=sharing&ouid=116800384344091292704&rtpof=true&sd=true)

## Estrutura de pacotes do projeto

| Pacote          | Descrição                                                                    |
| --------------- | ---------------------------------------------------------------------------- |
| config          | Classes de configuração.                                                     |
| controller      | Classes que gerenciam as requisições da API                                  | 
| dto             | Classes para transferencia de dados                                          |
| init            | Contém a classe StartApplication que popula a base de dados na inicialização |
| model           | Entidades do domínio da aplicação                                            |
| repository      | Inteface de acesso a base de dados                                           |
| security        | Classes relacionadas a segurança da aplicação                                |
| service         | Classes com regras de negócio                                                |

## Tecnologias utilizadas

- Spring Boot 3
- Spring Security
- Java 21
- ModelMapper
- Lombok
- H2 Database
- Springdoc (Swagger)
- Json Web Token

## Design patterns (em implementação)
### Singleton
### Facade
### Strategy
### Repository

## Links de documentação e teste da API em ambiente local
#### Swagger: [http://localhost:8080/swagger-ui.htm](http://localhost:8080/swagger-ui.html)
#### Console H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)


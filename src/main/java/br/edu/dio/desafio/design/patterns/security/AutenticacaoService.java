package br.edu.dio.desafio.design.patterns.security;

import br.edu.dio.desafio.design.patterns.dto.Login;
import br.edu.dio.desafio.design.patterns.dto.Sessao;
import br.edu.dio.desafio.design.patterns.model.Autenticacao;
import br.edu.dio.desafio.design.patterns.repository.AutenticacaoRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final AutenticacaoRepository autenticacaoRepository;
    private final PasswordEncoder encoder;
    private final SecurityConfig securityConfig;

    public AutenticacaoService(AutenticacaoRepository autenticacaoRepository, PasswordEncoder encoder, SecurityConfig securityConfig) {
        this.autenticacaoRepository = autenticacaoRepository;
        this.encoder = encoder;
        this.securityConfig = securityConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return autenticacaoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "não encontrado"));
    }

    public Sessao autenticarUsuario(Login login) {

        Optional<Autenticacao> autenticacao = autenticacaoRepository.findByUsername(login.getUsername());
        if (autenticacao.isEmpty()) {
            throw new UsernameNotFoundException("Usuário " + login.getUsername() + " não encontrado");
        }

        boolean passwordOk = encoder.matches(login.getPassword(), autenticacao.get().getPassword());
        if (!passwordOk) {
            throw new BadCredentialsException("Senha inválida para o login: " + login.getUsername());
        }

        //Estamos enviando um objeto Sessão para retornar mais informações do usuário
        Sessao sessao = new Sessao();
        sessao.setUsername(autenticacao.get().getUsername());

        JWTObject jwtObject = new JWTObject();
        jwtObject.setSubject(login.getUsername());
        jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
        jwtObject.setExpiration((new Date(System.currentTimeMillis() + securityConfig.getExpiration())));
        jwtObject.setRoles(autenticacao.get().getRoles());

        sessao.setToken(JWTCreator.create(securityConfig.getPrefix(), securityConfig.getKey(), jwtObject));
        return sessao;
    }

}

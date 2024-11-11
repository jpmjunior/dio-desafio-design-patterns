package br.edu.dio.desafio.design.patterns.service;

import br.edu.dio.desafio.design.patterns.dto.EnderecoDTO;
import br.edu.dio.desafio.design.patterns.external.facade.ViaCepClient;
import br.edu.dio.desafio.design.patterns.model.Autenticacao;
import br.edu.dio.desafio.design.patterns.model.Endereco;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import br.edu.dio.desafio.design.patterns.repository.AutenticacaoRepository;
import br.edu.dio.desafio.design.patterns.repository.UsuarioRepository;
import br.edu.dio.desafio.design.patterns.util.TestLogAppender;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AutenticacaoRepository autenticacaoRepository;

    @Mock
    private PasswordEncoder encoder;

    private TestLogAppender testLogAppender;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        // Adicionar o appender de teste
        Logger logger = (Logger) LoggerFactory.getLogger(UsuarioService.class);
        testLogAppender = new TestLogAppender();
        testLogAppender.start();
        logger.addAppender(testLogAppender);
    }

    @Test
    @DisplayName("Deve exibir log de erro quando ViaCEP retornar erro")
    void cadastrar_cenario1() {
        //Arrange
        Usuario usuario = Usuario.builder()
                .nome("Admin")
                .endereco(Endereco.builder().cep("65055000").build())
                .autenticacao(Autenticacao.builder()
                        .username("admin")
                        .password("master123")
                        .roles(List.of("MANAGERS"))
                        .build())
                .build();
        EnderecoDTO enderecoDTO = EnderecoDTO.builder().erro(true).build();
        when(encoder.encode(anyString())).thenReturn("senha_criptografada");
        when(viaCepClient.consultarCep(usuario.getEndereco().getCep())).thenReturn(enderecoDTO);
        when(autenticacaoRepository.save(any())).thenReturn(null);
        when(usuarioRepository.save(any())).thenReturn(null);

        //Act
        usuarioService.cadastrar(usuario);

        //Assert
        List<ILoggingEvent> logs = testLogAppender.getEvents();
        String logMessage = "CEP não encontrado";
        assertTrue(logs.stream().anyMatch(event -> event.getFormattedMessage().contains(logMessage)));
    }

}
package br.edu.dio.desafio.design.patterns.config.modelmapper;

import br.edu.dio.desafio.design.patterns.dto.UsuarioCadastroDTO;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UsuarioCadastroToUsuario extends PropertyMap<UsuarioCadastroDTO, Usuario> {
    @Override
    protected void configure() {
        map().getEndereco().setCep(source.getCep());
        map().getEndereco().setNumero(source.getNumero());
        map().getEndereco().setComplemento(source.getComplemento());
    }
}

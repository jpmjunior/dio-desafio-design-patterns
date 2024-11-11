package br.edu.dio.desafio.design.patterns.config.modelmapper;

import br.edu.dio.desafio.design.patterns.dto.UsuarioAtualizacaoDTO;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAtualizacaoToUsuario extends PropertyMap<UsuarioAtualizacaoDTO, Usuario> {
    @Override
    protected void configure() {
        map().getEndereco().setCep(source.getCep());
        map().getEndereco().setNumero(source.getNumero());
        map().getEndereco().setComplemento(source.getComplemento());
    }
}

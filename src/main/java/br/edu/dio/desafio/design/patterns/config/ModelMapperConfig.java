package br.edu.dio.desafio.design.patterns.config;

import br.edu.dio.desafio.design.patterns.dto.UserDTO;
import br.edu.dio.desafio.design.patterns.model.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<UserDTO, Usuario>() {
            @Override
            protected void configure() {
                map().getEndereco().setCep(source.getCep());
                map().getEndereco().setNumero(source.getNumero());
                map().getEndereco().setComplemento(source.getComplemento());
            }
        });
        return modelMapper;

    }

}

package br.edu.dio.desafio.design.patterns.config.modelmapper;

import br.edu.dio.desafio.design.patterns.model.Endereco;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@AllArgsConstructor
public class ModelMapperConfig {

    // lista injetada via construtor (lombok) com todas as classes que herdam de PropertyMap
    private final List<PropertyMap<?,?>> propertyMapsConfigs;

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT) // correspondência exata
                .setSkipNullEnabled(true); // ignora campos nulos na fonte

        // adiciona todos os mapeamentos personalizados da lista as configurações globais do modelmapper
        propertyMapsConfigs.forEach(modelMapper::addMappings);

        // ignora campos nulos do endereço de origem
        modelMapper.typeMap(Endereco.class, Endereco.class).setConverter(new GenericNullSkippingConverter<>());

        return modelMapper;

    }

}

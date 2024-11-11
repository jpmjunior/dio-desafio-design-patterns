package br.edu.dio.desafio.design.patterns.config.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Arrays;

// Converter genérico para origem e destino do mesmo tipo que ignora campos nulos da origem
public class GenericNullSkippingConverter<T> implements Converter<T, T> {
    @Override
    public T convert(MappingContext<T, T> context) {
        T source = context.getSource();
        T destination = context.getDestination();

        if (source != null && destination != null) {
            Arrays.stream(source.getClass().getDeclaredFields()).forEach(field -> {
                try {
                    field.setAccessible(true); // Permitir acesso a campos privados
                    Object sourceValue = field.get(source); // Obtém o valor do campo na origem
                    if (sourceValue != null) { // Verifica se o valor não é nulo
                        field.set(destination, sourceValue); // Define o valor no destino
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Erro ao acessar campo", e);
                }
            });
        }
        return destination;
    }
}

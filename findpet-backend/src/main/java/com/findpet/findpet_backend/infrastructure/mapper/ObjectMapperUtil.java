package com.findpet.findpet_backend.infrastructure.mapper;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/*
 * Classe utilitária responsável por converter objetos entre Entity e DTO.
 * Usa o ModelMapper para reduzir código manual de conversão.
 */
@Component
public class ObjectMapperUtil{

    private final ModelMapper modelMapper;

    public ObjectMapperUtil() {
        this.modelMapper = new ModelMapper();
    }

    /*
 * Converte um único objeto de uma classe de origem para uma classe de destino.
 */
    public <Origem, Destino> Destino map(Origem origem, Class<Destino> destinoClass) {
        return modelMapper.map(origem, destinoClass);
    }

        /*
     * Converte uma lista de objetos para uma lista de DTOs ou entidades.
     */

    public <Origem, Destino> List<Destino> mapAll(List<Origem> origemList, Class<Destino> destinoClass) {
        return origemList
                .stream()
                .map(origem -> map(origem, destinoClass))
                .collect(Collectors.toList());
    }
}
package com.findpet.findpet_backend.infrastructure.mapper;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperUtil{

    private final ModelMapper modelMapper;

    public ObjectMapperUtil() {
        this.modelMapper = new ModelMapper();
    }

    public <Origem, Destino> Destino map(Origem origem, Class<Destino> destinoClass) {
        return modelMapper.map(origem, destinoClass);
    }

    public <Origem, Destino> List<Destino> mapAll(List<Origem> origemList, Class<Destino> destinoClass) {
        return origemList
                .stream()
                .map(origem -> map(origem, destinoClass))
                .collect(Collectors.toList());
    }
}
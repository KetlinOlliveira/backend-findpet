package com.findpet.findpet_backend.animal.controller;

import com.findpet.findpet_backend.animal.dto.AnimalRequestDTO;
import com.findpet.findpet_backend.animal.dto.AnimalResponseDTO;
import com.findpet.findpet_backend.animal.enums.StatusAnimal;
import com.findpet.findpet_backend.animal.model.Animal;
import com.findpet.findpet_backend.animal.service.AnimalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/animais")
@CrossOrigin(origins = "*")
public class AnimalController implements IAnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Override
    public ResponseEntity<AnimalResponseDTO> cadastrar(AnimalRequestDTO animalRequestDTO) {
        Animal animal = new Animal();
        animal.setNome(animalRequestDTO.getNome());
        animal.setIdade(animalRequestDTO.getIdade());
        animal.setDescricao(animalRequestDTO.getDescricao());
        animal.setFotoUrl(animalRequestDTO.getFotoUrl());

        Animal animalSalvo = animalService.cadastrar(
                animal,
                animalRequestDTO.getUsuarioId(),
                animalRequestDTO.getEspecieId(),
                animalRequestDTO.getRacaId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponse(animalSalvo));
    }

    @Override
    public ResponseEntity<Page<AnimalResponseDTO>> listarTodos(StatusAnimal status, Pageable pageable) {
        Page<Animal> animais = status != null
                ? animalService.listarPorStatus(status, pageable)
                : animalService.listarTodos(pageable);

        return ResponseEntity.ok(animais.map(this::converterParaResponse));
    }

    @Override
    public ResponseEntity<AnimalResponseDTO> buscarPorId(Long id) {
        Animal animal = animalService.buscarPorId(id);

        return ResponseEntity.ok(converterParaResponse(animal));
    }

    @Override
    public ResponseEntity<AnimalResponseDTO> atualizar(Long id, AnimalRequestDTO animalRequestDTO) {
        Animal animalAtualizado = new Animal();
        animalAtualizado.setNome(animalRequestDTO.getNome());
        animalAtualizado.setIdade(animalRequestDTO.getIdade());
        animalAtualizado.setDescricao(animalRequestDTO.getDescricao());
        animalAtualizado.setFotoUrl(animalRequestDTO.getFotoUrl());

        Animal animal = animalService.atualizar(
                id,
                animalAtualizado,
                animalRequestDTO.getUsuarioId(),
                animalRequestDTO.getEspecieId(),
                animalRequestDTO.getRacaId()
        );

        return ResponseEntity.ok(converterParaResponse(animal));
    }

    @Override
    public ResponseEntity<Void> excluir(Long id) {
        animalService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    private AnimalResponseDTO converterParaResponse(Animal animal) {
        AnimalResponseDTO responseDTO = new AnimalResponseDTO();

        responseDTO.setId(animal.getId());
        responseDTO.setNome(animal.getNome());
        responseDTO.setIdade(animal.getIdade());
        responseDTO.setDescricao(animal.getDescricao());
        responseDTO.setStatus(animal.getStatus());
        responseDTO.setFotoUrl(animal.getFotoUrl());
        responseDTO.setDataCadastro(animal.getDataCadastro());

        if (animal.getUsuario() != null) {
            responseDTO.setUsuarioId(animal.getUsuario().getId());
            responseDTO.setUsuarioNome(animal.getUsuario().getNome());
        }

        if (animal.getEspecie() != null) {
            responseDTO.setEspecieId(animal.getEspecie().getId());
            responseDTO.setEspecieNome(animal.getEspecie().getNome());
        }

        if (animal.getRaca() != null) {
            responseDTO.setRacaId(animal.getRaca().getId());
            responseDTO.setRacaNome(animal.getRaca().getNome());
        }

        return responseDTO;
    }
}

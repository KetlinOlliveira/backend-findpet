package com.findpet.findpet_backend.animal.repository;

import com.findpet.findpet_backend.animal.enums.StatusAnimal;
import com.findpet.findpet_backend.animal.model.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    boolean existsByEspecieId(Long especieId);

    boolean existsByRacaId(Long racaId);

    Page<Animal> findByStatus(StatusAnimal status, Pageable pageable);
}

package com.findpet.findpet_backend.animal.model;


import com.findpet.findpet_backend.animal.enums.StatusAnimal;
import com.findpet.findpet_backend.especie.model.Especie;
import com.findpet.findpet_backend.raca.model.Raca;
import com.findpet.findpet_backend.usuario.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animais")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do animal é obrigatório.")
    private String nome;

    private Integer idade;

    private String porte;

    private String sexo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAnimal status = StatusAnimal.DISPONIVEL;

    @Column(name = "foto_url", columnDefinition = "TEXT")
    private String fotoUrl;

    private LocalDateTime dataCadastro = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "especie_id", nullable = false)
    private Especie especie;

    @ManyToOne
    @JoinColumn(name = "raca_id", nullable = false)
    private Raca raca;


    // Gere os getters e setters pelo VS Code/IntelliJ
}
package com.findpet.findpet_backend.pessoa.model;

import com.findpet.findpet_backend.endereco.model.Endereco;
import com.findpet.findpet_backend.usuario.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O CPF é obrigatório.")
    @Column(unique = true)
    private String cpf;

    private String telefone;

    private LocalDate dataNascimento;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", nullable = false, unique = true)
    private Endereco endereco;

}
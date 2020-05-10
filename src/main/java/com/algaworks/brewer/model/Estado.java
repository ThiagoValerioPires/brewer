package com.algaworks.brewer.model;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "A sigla é obrigatória")
    @Size(max=2, min=2, message = "O tamanho do nome deve ser 2")
    private String sigla;

}

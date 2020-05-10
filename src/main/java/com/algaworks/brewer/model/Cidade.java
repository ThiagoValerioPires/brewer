package com.algaworks.brewer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_estado")
    @JsonIgnore
    @NotNull(message = "Estado é obrigatório")
    private Estado estado;

    public boolean temEstado(){
        return estado != null;
    }

}

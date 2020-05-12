package com.algaworks.brewer.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class UsuarioGrupoId implements Serializable {

    @ManyToOne
    @JoinColumn(name="codigo_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="codigo_grupo")
    private Grupo grupo;
}

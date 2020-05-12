package com.algaworks.brewer.repository.filter;


import com.algaworks.brewer.model.Grupo;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioFilter {

    private String nome;
    private String email;
    private List<Grupo> grupos;

}

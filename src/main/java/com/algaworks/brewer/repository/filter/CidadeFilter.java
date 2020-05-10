package com.algaworks.brewer.repository.filter;

import com.algaworks.brewer.model.Estado;
import lombok.Data;

@Data
public class CidadeFilter {

    private String nome;
    private Estado estado;

}

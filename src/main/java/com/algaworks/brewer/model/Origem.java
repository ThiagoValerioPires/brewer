package com.algaworks.brewer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Origem {

    NACIONAL("Nacional"),
    INTERNACIONAL("Internacional");

    private String descricao;

}

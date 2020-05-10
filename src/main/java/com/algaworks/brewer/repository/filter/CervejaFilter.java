package com.algaworks.brewer.repository.filter;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CervejaFilter {

    private String sku;
    private String nome;
    private Estilo estilo;
    private Sabor sabor;
    private Origem origem;
    private BigDecimal valorDe;
    private BigDecimal valorAte;

}

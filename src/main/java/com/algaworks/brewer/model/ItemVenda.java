package com.algaworks.brewer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ItemVenda {


    private Long codigo;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private Cerveja cerveja;

    public ItemVenda(Integer quantidade, BigDecimal valorUnitario, Cerveja cerveja) {
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.cerveja = cerveja;
    }

    public BigDecimal getValorTotal(){
        return valorUnitario.multiply(new BigDecimal(quantidade));
    }

}

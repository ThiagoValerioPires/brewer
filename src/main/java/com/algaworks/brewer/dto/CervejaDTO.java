package com.algaworks.brewer.dto;

import com.algaworks.brewer.model.Origem;
import lombok.Data;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;

@Data
public class CervejaDTO {

    private Long codigo;
    private String sku;
    private String nome;
    private String origem;
    private BigDecimal valor;
    private String foto;

    public CervejaDTO(Long codigo,
                      String sku,
                      String nome,
                      Origem origem,
                      BigDecimal valor,
                      String foto) {
        this.codigo = codigo;
        this.sku = sku;
        this.nome = nome;
        this.origem = origem.getDescricao();
        this.valor = valor;
        this.foto = StringUtils.isEmpty(foto) ? "cerveja-mock.png" : foto;
    }
}

package com.algaworks.brewer.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Data
@Embeddable
public class Endereco {

    private String logradouro;

    private String numero;

    private String complemento;

    private String cep;

    @ManyToOne
    @JoinColumn(name = "codigo_cidade")
    private Cidade cidade;

    @Transient
    private Estado estado;


    public String getNomeCidadeSiglaEstado(){
        if(this.cidade != null){
            return cidade.getNome() + "/" + cidade.getEstado().getSigla();
        }
        return null;
    }

}

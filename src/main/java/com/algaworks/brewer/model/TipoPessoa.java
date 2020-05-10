package com.algaworks.brewer.model;

import com.algaworks.brewer.model.validation.group.CnpjGroup;
import com.algaworks.brewer.model.validation.group.CpfGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoPessoa {

    FISICA("Física","CPF","000.000.000-00", CpfGroup.class){

        @Override
        public String formatar(String cpfOuCnpj){
            return cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})","$1.$2.$3-");
        }

    },
    JURIDICA("Jurídica","CNPJ","00.000.000/0000-00", CnpjGroup.class){

        @Override
        public String formatar(String cpfOuCnpj) {
            return cpfOuCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})","$1.$2.$3/$4-");
        }

    };

    private String descricao;
    private String documento;
    private String mascara;
    private Class<?> grupo;

    public abstract String formatar(String cpfOuCnpj);

    public static String removerFormatacao(String cpfOuCnpj){
        return cpfOuCnpj.replaceAll("\\.|-|/","");
    }


}

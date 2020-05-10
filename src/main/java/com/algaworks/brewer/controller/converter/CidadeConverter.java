package com.algaworks.brewer.controller.converter;

import com.algaworks.brewer.model.Cidade;
import org.springframework.core.convert.converter.Converter;
import org.thymeleaf.util.StringUtils;

public class CidadeConverter implements Converter<String, Cidade> {

    @Override
    public Cidade convert(String codigo) {
        if(!StringUtils.isEmpty(codigo)) {
            Cidade cidade = new Cidade();
            cidade.setCodigo(Long.valueOf(codigo));
            return cidade;
        }
        return null;
    }
}
package com.algaworks.brewer.service;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.CidadeRepository;
import com.algaworks.brewer.service.exception.NomeCidadeJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Transactional
    public Cidade salvar(Cidade cidade) throws NomeCidadeJaCadastradoException {

        if(cidadeRepository.findByEstadoAndNomeIgnoreCase(cidade.getEstado(),cidade.getNome()).isPresent()){
            throw new NomeCidadeJaCadastradoException("Nome da cidade já cadastrado");
        }

        return cidadeRepository.saveAndFlush(cidade);
    }
}

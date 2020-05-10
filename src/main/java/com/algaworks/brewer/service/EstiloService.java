package com.algaworks.brewer.service;

import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.EstiloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstiloService {

    @Autowired
    private EstiloRepository estiloRepository;

    @Transactional
    public Estilo salvar(Estilo estilo) throws NomeEstiloJaCadastradoException {

        if(estiloRepository.findByNomeIgnoreCase(estilo.getNome()).isPresent()){
            throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
        }

        return estiloRepository.saveAndFlush(estilo);
    }
}

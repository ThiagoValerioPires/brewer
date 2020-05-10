package com.algaworks.brewer.service;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.CervejaRepository;
import com.algaworks.brewer.service.event.cerveja.CervejaSalvaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CervejaService {

    @Autowired
    private CervejaRepository cervejaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Transactional
    public void salvar(Cerveja cerveja){
        cervejaRepository.save(cerveja);
        publisher.publishEvent(new CervejaSalvaEvent(cerveja));
    }

}

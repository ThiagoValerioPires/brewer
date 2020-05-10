package com.algaworks.brewer.service.event.cerveja;

import com.algaworks.brewer.storage.FotoStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CervejaListener {

    @Autowired
    private FotoStorage fotoStorage;

    @EventListener(condition = "#evento.temFoto()")
    public void cervejaSalva(CervejaSalvaEvent evento){
        fotoStorage.salvar(evento.getCerveja().getFoto());
    }


}

package com.algaworks.brewer.service.event.cerveja;

import com.algaworks.brewer.model.Cerveja;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Getter
public class CervejaSalvaEvent {

    private Cerveja cerveja;

    public boolean temFoto(){
        return !StringUtils.isEmpty(cerveja.getFoto());
    }

}

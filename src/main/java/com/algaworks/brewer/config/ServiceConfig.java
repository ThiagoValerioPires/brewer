package com.algaworks.brewer.config;

import com.algaworks.brewer.repository.CervejaRepository;
import com.algaworks.brewer.service.CervejaService;
import com.algaworks.brewer.storage.FotoStorage;
import com.algaworks.brewer.storage.local.FotoStorageLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CervejaService.class)
public class ServiceConfig {

    @Bean
    public FotoStorage fotoStorageLocal() {
        return new FotoStorageLocal();
    }


}
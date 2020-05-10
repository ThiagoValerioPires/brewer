package com.algaworks.brewer.repository.helper.estilo;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstiloQueries {

    Page<Estilo> filtar(EstiloFilter filtro, Pageable pageable);
}

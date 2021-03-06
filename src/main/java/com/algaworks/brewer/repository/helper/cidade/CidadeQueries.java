package com.algaworks.brewer.repository.helper.cidade;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CidadeQueries {

    Page<Cidade> filtar(CidadeFilter filtro, Pageable pageable);

}

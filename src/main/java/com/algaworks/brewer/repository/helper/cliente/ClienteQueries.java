package com.algaworks.brewer.repository.helper.cliente;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteQueries {

    Page<Cliente> filtar(ClienteFilter filtro, Pageable pageable);

}

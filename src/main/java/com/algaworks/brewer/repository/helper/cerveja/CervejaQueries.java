package com.algaworks.brewer.repository.helper.cerveja;


import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CervejaQueries {

    Page<Cerveja> filtar(CervejaFilter filtro, Pageable pageable);

    List<CervejaDTO> porSkuOuNome(String skuOuNome);

}

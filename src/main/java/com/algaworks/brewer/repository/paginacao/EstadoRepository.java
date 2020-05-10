package com.algaworks.brewer.repository.paginacao;

import com.algaworks.brewer.model.Estado;
import com.algaworks.brewer.model.Estilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado,Long> {

}

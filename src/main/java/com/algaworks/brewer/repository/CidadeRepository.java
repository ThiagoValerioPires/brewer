package com.algaworks.brewer.repository;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.model.Estado;
import com.algaworks.brewer.repository.helper.cidade.CidadeQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade,Long>, CidadeQueries {

    List<Cidade> findByEstadoCodigo(Long codigoEstado);

    Optional<Object> findByEstadoAndNomeIgnoreCase(Estado estado, String nome);
}

package com.algaworks.brewer.repository;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.helper.estilo.EstiloQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstiloRepository extends JpaRepository<Estilo,Long> , EstiloQueries {

    Optional<Estilo> findByNomeIgnoreCase(String nome);

}

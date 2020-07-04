package com.algaworks.brewer.repository;


import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.helper.cliente.ClienteQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>, ClienteQueries {

    Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

    List<Cliente> findByNomeStartingWithIgnoreCase(String nome);
}

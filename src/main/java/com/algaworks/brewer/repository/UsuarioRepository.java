package com.algaworks.brewer.repository;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.helper.usuario.UsuarioQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario,Long>, UsuarioQueries {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByCodigoIn(Long[] codigos);
}
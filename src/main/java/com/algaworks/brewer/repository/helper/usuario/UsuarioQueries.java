package com.algaworks.brewer.repository.helper.usuario;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioQueries {

    Optional<Usuario> porEmailEAtivo(String email);

    List<String> permissoes(Usuario usuario);

    Page<Usuario> filtar(UsuarioFilter filtro, Pageable pageable);

}

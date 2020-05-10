package com.algaworks.brewer.repository.helper.usuario;

import com.algaworks.brewer.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositoryImpl implements UsuarioQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Usuario> porEmailEAtivo(String email) {
        return manager.createQuery("from Usuario where email = :email and ativo = true",Usuario.class)
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }

    @Override
    public List<String> permissoes(Usuario usuario) {
        return manager.createQuery(
                "select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u.email = :email",String.class)
                .setParameter("email", usuario.getEmail())
                .getResultList();
    }


}

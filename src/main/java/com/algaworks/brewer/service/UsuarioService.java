package com.algaworks.brewer.service;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.UsuarioRepository;
import com.algaworks.brewer.service.exception.EmailJaCadastradoException;
import com.algaworks.brewer.service.exception.SenhaObrigatoriaUsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new EmailJaCadastradoException("E-mail já cadastrado");
        }

        if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())){
            throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
        }

        if(usuario.isNovo()){
            usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
            usuario.setConfirmacaoSenha(usuario.getSenha());
        }

        return usuarioRepository.saveAndFlush(usuario);
    }
}

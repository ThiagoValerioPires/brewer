package com.algaworks.brewer.service;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.ClienteRepository;
import com.algaworks.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {

        Optional<Cliente> clienteExistente = clienteRepository.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());

        if(clienteExistente.isPresent()){
            throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
        }

        return clienteRepository.saveAndFlush(cliente);
    }
}
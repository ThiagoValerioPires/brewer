package com.algaworks.brewer.controller;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.repository.ClienteRepository;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.repository.paginacao.EstadoRepository;
import com.algaworks.brewer.service.ClienteService;
import com.algaworks.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/novo")
    public ModelAndView novo(Cliente cliente){
        ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
        mv.addObject("tiposPessoa", TipoPessoa.values());
        mv.addObject("estados", estadoRepository.findAll());
        return mv;
    }

    @PostMapping(value="/novo")
    public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult bindingResult, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            return novo(cliente);
        }
        try {
            clienteService.salvar(cliente);
        }catch (CpfCnpjClienteJaCadastradoException e){
            bindingResult.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
            return novo(cliente);
        }
        attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
        return new ModelAndView("redirect:/clientes/novo");
    }


    @GetMapping
    public ModelAndView pesquisar(ClienteFilter clienteFilter, BindingResult result, @PageableDefault(size=2) Pageable pageable, HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView("cliente/PesquisaClientes");

        PageWrapper<Cliente> paginaWrapper = new PageWrapper<>(clienteRepository.filtar(clienteFilter, pageable), httpServletRequest);
        mv.addObject("pagina", paginaWrapper );
        return mv;
    }


}

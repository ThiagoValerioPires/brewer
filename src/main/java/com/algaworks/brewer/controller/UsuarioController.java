package com.algaworks.brewer.controller;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.paginacao.GrupoRepository;
import com.algaworks.brewer.service.UsuarioService;
import com.algaworks.brewer.service.exception.EmailJaCadastradoException;
import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;
import com.algaworks.brewer.service.exception.SenhaObrigatoriaUsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoRepository grupoRepository;


    @GetMapping("/novo")
    public ModelAndView novo(Usuario usuario){
        ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
        mv.addObject("grupos", grupoRepository.findAll());
        return mv;
    }

    @PostMapping(value="/novo")
    public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            return novo(usuario);
        }

        try {
            usuarioService.salvar(usuario);
        }catch(EmailJaCadastradoException e){
            bindingResult.rejectValue("email", e.getMessage(), e.getMessage());
            return novo(usuario);
        }catch (SenhaObrigatoriaUsuarioException e){
            bindingResult.rejectValue("senha", e.getMessage(), e.getMessage());
            return novo(usuario);
        }


        attributes.addFlashAttribute("mensagem", "Salvo com sucesso");

        return new ModelAndView("redirect:/usuarios/novo");
    }
}

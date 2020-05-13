package com.algaworks.brewer.controller;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.UsuarioRepository;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import com.algaworks.brewer.repository.paginacao.GrupoRepository;
import com.algaworks.brewer.service.UsuarioService;
import com.algaworks.brewer.service.exception.EmailJaCadastradoException;
import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;
import com.algaworks.brewer.service.exception.SenhaObrigatoriaUsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;


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

    @GetMapping
    public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result, @PageableDefault(size=2) Pageable pageable, HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");
        mv.addObject("grupos", grupoRepository.findAll());

        PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarioRepository.filtar(usuarioFilter,pageable), httpServletRequest);
        mv.addObject("pagina", paginaWrapper );
        return mv;
    }

    @PutMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") String status){
        usuarioService.alterarStatus(codigos,status);
    }


}

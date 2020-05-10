package com.algaworks.brewer.controller;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.EstiloRepository;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.service.EstiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/estilos")
public class EstiloController {

    @Autowired
    private EstiloService estiloService;

    @Autowired
    private EstiloRepository estiloRepository;

    @GetMapping("/novo")
    public ModelAndView novo(Estilo estilo){
        ModelAndView mv = new ModelAndView("/estilo/CadastroEstilo");

        return mv;
    }

    @PostMapping(value="/novo")
    public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult bindingResult, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            return novo(estilo);
        }
        try {
            estiloService.salvar(estilo);
        }catch(NomeEstiloJaCadastradoException e){
            bindingResult.rejectValue("nome", e.getMessage(), e.getMessage());
            return novo(estilo);
        }

        attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
        return new ModelAndView( "redirect:/estilos/novo");
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) throws NomeEstiloJaCadastradoException {

        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
        }

        estilo = estiloService.salvar(estilo);

        return ResponseEntity.ok(estilo);
    }

    @GetMapping
    public ModelAndView pesquisar(EstiloFilter estiloFilter, BindingResult result, @PageableDefault(size=2) Pageable pageable, HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView("estilo/PesquisaEstilos");

        PageWrapper<Estilo> paginaWrapper = new PageWrapper<>(estiloRepository.filtar(estiloFilter, pageable), httpServletRequest);
        mv.addObject("pagina", paginaWrapper );
        return mv;
    }
}

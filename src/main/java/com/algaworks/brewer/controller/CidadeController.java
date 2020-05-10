package com.algaworks.brewer.controller;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.CidadeRepository;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.repository.paginacao.EstadoRepository;
import com.algaworks.brewer.service.CidadeService;
import com.algaworks.brewer.service.exception.NomeCidadeJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping("/novo")
    public ModelAndView novo(Cidade cidade){
        ModelAndView mv = new ModelAndView("/cidade/CadastroCidade");
        mv.addObject("estados", estadoRepository.findAll());
        return mv;
    }

    @PostMapping(value="/novo")
    @CacheEvict(value = "cidades", key="#cidade.estado.codigo", condition = "#cidade.temEstado()")
    public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult bindingResult, RedirectAttributes attributes){

        if(bindingResult.hasErrors()){
            return novo(cidade);
        }
        try {
            cidadeService.salvar(cidade);
        }catch (NomeCidadeJaCadastradoException e){
            bindingResult.rejectValue("nome", e.getMessage(), e.getMessage());
            return novo(cidade);
        }

        attributes.addFlashAttribute("mensagem", "Salvo com sucesso");

        return new ModelAndView("redirect:/cidades/novo");
    }

    @Cacheable(value = "cidades", key = "#codigoEstado")
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(
            @RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

        return cidadeRepository.findByEstadoCodigo(codigoEstado);
    }

    @GetMapping
    public ModelAndView pesquisar(CidadeFilter cidadeFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");

        PageWrapper<Cidade> paginaWrapper = new PageWrapper<>(cidadeRepository.filtar(cidadeFilter, pageable), httpServletRequest);
        mv.addObject("pagina", paginaWrapper );
        mv.addObject("estados",estadoRepository.findAll());
        return mv;
    }

}

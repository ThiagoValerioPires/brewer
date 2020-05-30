package com.algaworks.brewer.controller;


import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import com.algaworks.brewer.repository.CervejaRepository;
import com.algaworks.brewer.repository.EstiloRepository;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.service.CervejaService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cervejas")
public class CervejaController {

    @Autowired
    private CervejaService cervejaService;

    @Autowired
    private EstiloRepository estiloRepository;

    @Autowired
    private CervejaRepository cervejaRepository;

    @GetMapping("/novo")
    public ModelAndView novo(Cerveja cerveja){
        ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
        mv.addObject("sabores", Sabor.values());
        mv.addObject("estilos", estiloRepository.findAll());
        mv.addObject("origens", Origem.values());
        return mv;
    }


    @PostMapping(value="/novo")
    public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult bindingResult, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            return novo(cerveja);
        }

        cervejaService.salvar(cerveja);
        attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
        return new ModelAndView("redirect:/cervejas/novo");
    }

    @GetMapping
    public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size=2) Pageable pageable, HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");
        mv.addObject("sabores", Sabor.values());
        mv.addObject("estilos", estiloRepository.findAll());
        mv.addObject("origens", Origem.values());



        PageWrapper<Cerveja> paginaWrapper = new PageWrapper<>(cervejaRepository.filtar(cervejaFilter, pageable), httpServletRequest);
        mv.addObject("pagina", paginaWrapper );
        return mv;
    }

    @GetMapping("/filtro")
    public @ResponseBody List<CervejaDTO> pesquisar(String skuOuNome){
        return cervejaRepository.porSkuOuNome(skuOuNome);
    }



}

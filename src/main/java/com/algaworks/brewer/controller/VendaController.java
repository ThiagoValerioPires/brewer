package com.algaworks.brewer.controller;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.CervejaRepository;
import com.algaworks.brewer.session.TabelaItensSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/vendas")
@Slf4j
public class VendaController {

    @Autowired
    CervejaRepository cervejaRepository;

    @Autowired
    TabelaItensSession tabelaItens;

    @GetMapping("/novo")
    public ModelAndView novo(){
        ModelAndView mv = new ModelAndView("venda/CadastroVenda");
        mv.addObject("uuid", UUID.randomUUID().toString());
        return mv;
    }

    @PostMapping("/item")
    public ModelAndView adicionarItem(Long codigoCerveja, String uuid){
        Cerveja cerveja = cervejaRepository.findOne(codigoCerveja);
        tabelaItens.adicionarItem(uuid, cerveja,1);
        return mvTabelaItensVenda(uuid);
    }

    @PutMapping("item/{codigoCerveja}")
    public ModelAndView alterarQuantidadeItem(@PathVariable("codigoCerveja") Cerveja cerveja,
                                              Integer quantidade, String uuid){
        tabelaItens.alterarQuantidadeItens(uuid,cerveja,quantidade);
        return mvTabelaItensVenda(uuid);
    }

    @DeleteMapping("/item/{uuid}/{codigoCerveja}")
    public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja, @PathVariable String uuid){
        tabelaItens.excluirItem(uuid, cerveja);
        return mvTabelaItensVenda(uuid);
    }

    private ModelAndView mvTabelaItensVenda(String uuid) {
        ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
        mv.addObject("itens", tabelaItens.getItens(uuid));
        return mv;
    }

}

package com.algaworks.brewer.controller;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.CervejaRepository;
import com.algaworks.brewer.session.TabelaItensVenda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/vendas")
@Slf4j
public class VendaController {

    @Autowired
    CervejaRepository cervejaRepository;

    @Autowired
    TabelaItensVenda tabelaItensVenda;

    @GetMapping("/novo")
    public String novo(){
        return "venda/CadastroVenda";
    }

    @PostMapping("/item")
    public ModelAndView adicionarItem(Long codigoCerveja){
        Cerveja cerveja = cervejaRepository.findOne(codigoCerveja);
        tabelaItensVenda.adicionarItem(cerveja,1);
        return mvTabelaItensVenda();
    }

    @PutMapping("item/{codigoCerveja}")
    public ModelAndView alterarQuantidadeItem(@PathVariable("codigoCerveja") Cerveja cerveja, Integer quantidade){
        tabelaItensVenda.alterarQuantidadeItens(cerveja,quantidade);
        return mvTabelaItensVenda();
    }

    @DeleteMapping("/item/{codigoCerveja}")
    public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja){
        tabelaItensVenda.excluirItem(cerveja);
        return mvTabelaItensVenda();
    }

    private ModelAndView mvTabelaItensVenda() {
        ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
        mv.addObject("itens", tabelaItensVenda.getItens());
        return mv;
    }

}

package com.algaworks.brewer.session;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.session.TabelaItensVenda;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TabelaItensVendaTest {

    private TabelaItensVenda tabelaItensVenda;

    @Before
    public void setUp(){
        this.tabelaItensVenda = new TabelaItensVenda("1");
    }

    @Test
    public void deveCalcularValorTotalSemItens() {
        assertEquals(BigDecimal.ZERO,tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveCalcularValorTotalComUmItem(){

        BigDecimal valor = new BigDecimal("8.90");
        Cerveja cerveja = new Cerveja();
        cerveja.setValor(valor);
        tabelaItensVenda.adicionarItem(cerveja, 1);
        assertEquals(valor,tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveCalcularValorTotalComVariosItens(){

        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);
        BigDecimal v1 = new BigDecimal("8.90");
        c1.setValor(v1);

        Cerveja c2 = new Cerveja();
        c2.setCodigo(2L);
        BigDecimal v2 = new BigDecimal("4.99");
        c2.setValor(v2);

        tabelaItensVenda.adicionarItem(c1, 1);
        tabelaItensVenda.adicionarItem(c2, 2);

        assertEquals(v1.add(v2.multiply(BigDecimal.valueOf(2))),tabelaItensVenda.getValorTotal());
    }


    @Test
    public void deveManterTamanhoDaListParaMesmasCervejas(){
        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);
        BigDecimal v1 = new BigDecimal("8.90");
        c1.setValor(v1);

        tabelaItensVenda.adicionarItem(c1,1);
        tabelaItensVenda.adicionarItem(c1,1);

        assertEquals(1,tabelaItensVenda.total());

    }

    @Test
    public void deveAlterarQuantidadeDoItem(){
        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);
        BigDecimal v1 = new BigDecimal("8.90");
        c1.setValor(v1);

        tabelaItensVenda.adicionarItem(c1,1);
        tabelaItensVenda.alterarQuantidadeItens(c1,3);

        assertEquals(v1.multiply(BigDecimal.valueOf(3)),tabelaItensVenda.getValorTotal());

    }

    @Test
    public void deveExcluirItem(){

        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);
        BigDecimal v1 = new BigDecimal("8.90");
        c1.setValor(v1);

        Cerveja c2 = new Cerveja();
        c2.setCodigo(2L);
        BigDecimal v2 = new BigDecimal("4.99");
        c2.setValor(v2);

        Cerveja c3 = new Cerveja();
        c3.setCodigo(3L);
        BigDecimal v3 = new BigDecimal("4.01");
        c3.setValor(v3);

        tabelaItensVenda.adicionarItem(c1,1);
        tabelaItensVenda.adicionarItem(c2,2);
        tabelaItensVenda.adicionarItem(c3,1);

        tabelaItensVenda.excluirItem(c2);

        assertEquals(2, tabelaItensVenda.total());
        assertEquals(v1.add(v3),tabelaItensVenda.getValorTotal());
    }



}

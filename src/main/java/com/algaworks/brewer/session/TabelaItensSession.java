package com.algaworks.brewer.session;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.ItemVenda;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SessionScope
@Component
public class TabelaItensSession {

    private Set<TabelaItensVenda> tabelas = new HashSet<>();

    public void adicionarItem(String uuid, Cerveja cerveja, int quantidade) {
        TabelaItensVenda tabela = buscatTabelaPorUuid(uuid);
        tabela.adicionarItem(cerveja, quantidade);
        tabelas.add(tabela);
    }

    public List<ItemVenda> getItens(String uuid) {
        return buscatTabelaPorUuid(uuid).getItens();
    }

    public void alterarQuantidadeItens(String uuid, Cerveja cerveja, Integer quantidade) {
        buscatTabelaPorUuid(uuid).alterarQuantidadeItens(cerveja,quantidade);
    }

    public void excluirItem(String uuid, Cerveja cerveja) {
        buscatTabelaPorUuid(uuid).excluirItem(cerveja);
    }

    private TabelaItensVenda buscatTabelaPorUuid(String uuid) {
        return tabelas.stream()
                .filter(t -> t.getUuid().equals(uuid))
                .findAny().orElse(new TabelaItensVenda(uuid));
    }

    public BigDecimal getValorTotal(String uuid) {
        return buscatTabelaPorUuid(uuid).getValorTotal();
    }
}

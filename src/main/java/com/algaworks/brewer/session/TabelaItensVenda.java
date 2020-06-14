package com.algaworks.brewer.session;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.ItemVenda;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SessionScope
@Component
@Getter
public class TabelaItensVenda {

    private final List<ItemVenda> itens = new ArrayList<>();

    public BigDecimal getValorTotal(){
        return itens.stream()
                .map(ItemVenda::getValorTotal)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void adicionarItem(Cerveja cerveja, Integer quantidade){

        Optional<ItemVenda> itemVendaOptional = buscarItemPorCerveja(cerveja);

        if(itemVendaOptional.isPresent()){
            ItemVenda itemVenda = itemVendaOptional.get();
            itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
        }else {
            ItemVenda itemVenda = new ItemVenda(quantidade, cerveja.getValor(), cerveja);
            itens.add(0,itemVenda);
        }
    }

    public void alterarQuantidadeItens(Cerveja cerveja, Integer quantidade){
        ItemVenda itemVenda = buscarItemPorCerveja(cerveja).get();
        itemVenda.setQuantidade(quantidade);

    }

    public void excluirItem(Cerveja cerveja){
        int indice = IntStream
                .range(0, itens.size())
                .filter(i -> itens.get(i).getCerveja().equals(cerveja))
                .findAny()
                .getAsInt();
        itens.remove(indice);
    }

    public int total(){
        return itens.size();
    }

    private Optional<ItemVenda> buscarItemPorCerveja(Cerveja cerveja) {
        return itens.stream()
                .filter(i -> i.getCerveja().equals(cerveja))
                .findAny();
    }

}

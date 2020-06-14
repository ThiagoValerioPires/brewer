Brewer.TabelaItens = (function () {

    function  TabelaItens(autocomplete) {
        this.autocomplete = autocomplete;
        this.tabelaCervejaContainer = $('.js-tabela-cervejas-container');
    }

    TabelaItens.prototype.iniciar = function() {
        this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));
    }

    function onItemSelecionado(evento, item){
        const resposta = $.ajax({
            url: 'item',
            method: 'POST',
            data: {
                codigoCerveja: item.codigo
            }
        });

        resposta.done(onItemAtualizadoNoServidor.bind(this));
    }

    function onItemAtualizadoNoServidor(html){
        this.tabelaCervejaContainer.html(html);
        $('.js-tabela-cerveja-quantidade-item').on('change', onQuantidadeItemAlterado.bind(this))
        $('.js-tabela-item').on('dblclick', onDoubleClick);
        $('.js-exclusao-item-btn').on('click',onExclusaoItemClick.bind(this))
    }
    
    function onQuantidadeItemAlterado(evento) {
        const input = $(evento.target);
        const quantidade = input.val();
        const codigoCerveja = input.data('codigo-cerveja');

        const resposta = $.ajax({
            url: 'item/'+codigoCerveja,
            method: 'PUT',
            data: {
                quantidade: quantidade,
            }
        });

        resposta.done(onItemAtualizadoNoServidor.bind(this));
    }

    function onDoubleClick(evento){
        $(this).toggleClass('solicitando-exclusao');
    }

    function onExclusaoItemClick(evento){
        const codigoCerveja = $(evento.target).data('codigo-cerveja');
        const resposta = $.ajax({
            url: 'item/'+codigoCerveja,
            method: 'DELETE'
        });

        resposta.done(onItemAtualizadoNoServidor.bind(this));
    }

    return TabelaItens;

}());

$(function () {
    let autocomplete = new Brewer.Autocomplete();
    autocomplete.iniciar();

    let tabelaItens = new Brewer.TabelaItens(autocomplete);
    tabelaItens.iniciar();
});
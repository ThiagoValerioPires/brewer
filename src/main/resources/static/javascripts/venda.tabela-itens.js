Brewer.TabelaItens = (function () {

    function  TabelaItens(autocomplete) {
        this.autocomplete = autocomplete;
        this.tabelaCervejaContainer = $('.js-tabela-cervejas-container');
        this.uuid = $('#uuid').val();
        this.emitter = $({});
        this.on = this.emitter.on.bind(this.emitter);
    }

    TabelaItens.prototype.iniciar = function() {
        this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));
    }

    function onItemSelecionado(evento, item){
        const resposta = $.ajax({
            url: 'item',
            method: 'POST',
            data: {
                codigoCerveja: item.codigo,
                uuid: this.uuid
            }
        });

        resposta.done(onItemAtualizadoNoServidor.bind(this));
    }

    function onItemAtualizadoNoServidor(html){
        this.tabelaCervejaContainer.html(html);
        const quantidadeItemInput = $('.js-tabela-cerveja-quantidade-item');
        quantidadeItemInput.on('change', onQuantidadeItemAlterado.bind(this));
        quantidadeItemInput.maskMoney({precision: 0, thousands: ''});
        let tabelaItem = $('.js-tabela-item');
        tabelaItem.on('dblclick', onDoubleClick);
        $('.js-exclusao-item-btn').on('click',onExclusaoItemClick.bind(this));

        this.emitter.trigger('tabela-itens-atualizada', tabelaItem.data('valor-total'));
    }
    
    function onQuantidadeItemAlterado(evento) {
        const input = $(evento.target);
        let quantidade = input.val();

        if(quantidade <= 0){
            input.val(1);
            quantidade = 1;
        }

        const codigoCerveja = input.data('codigo-cerveja');

        const resposta = $.ajax({
            url: 'item/'+codigoCerveja,
            method: 'PUT',
            data: {
                quantidade: quantidade,
                uuid: this.uuid
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
            url: 'item/'+ this.uuid +'/'+ codigoCerveja,
            method: 'DELETE'
        });

        resposta.done(onItemAtualizadoNoServidor.bind(this));
    }

    return TabelaItens;

}());


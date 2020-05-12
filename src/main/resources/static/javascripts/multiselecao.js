var Brewer = Brewer || {};

Brewer.MultiSelecao = (function(){

    function MultiSelecao() {
        this.statusBtn = $('.js-status-btn');
        this.selecaoCheckbox = $('.js-selecao');
        this.selecaoTodosCheckbox = $('.js-selecao-todos');
    }

    MultiSelecao.prototype.iniciar = function () {
        this.statusBtn.on('click', onStatusBtnClicado.bind(this));
        this.selecaoTodosCheckbox.on('click',onSelecaoTodosClicado.bind(this));
        this.selecaoCheckbox.on('click',onSelecaoClicado.bind(this));
    }

    function onStatusBtnClicado(event) {
        let botaoClicado = $(event.currentTarget);
        let status = botaoClicado.data('status');
        let url = botaoClicado.data('url');
        let checkboxSelecionados = this.selecaoCheckbox.filter(':checked');
        let codigos = $.map(checkboxSelecionados, function(c){
            return $(c).data('codigo');
        });

        if(codigos.length > 0) {
            $.ajax({
                url: url,
                method: 'PUT',
                data: {
                    codigos: codigos,
                    status: status
                },
                success: function () {
                    window.location.reload();
                }
            });
        }
    }


    function onSelecaoTodosClicado() {
        let status = this.selecaoTodosCheckbox.prop('checked');
        this.selecaoCheckbox.prop('checked', status);
        statusBotaoAcao.call(this,status);
    }

    function onSelecaoClicado() {
        let selecaoCheckboxChecados = this.selecaoCheckbox.filter(':checked');
        let selecionado = selecaoCheckboxChecados.length >= this.selecaoCheckbox.length;
        this.selecaoTodosCheckbox.prop('checked', selecionado);
        statusBotaoAcao.call(this, selecaoCheckboxChecados.length);
    }

    function statusBotaoAcao(ativar) {
        ativar ? this.statusBtn.removeClass('disabled') : this.statusBtn.addClass('disabled');

    }

    return MultiSelecao;


}());

$(function(){
    let multiSelecao = new Brewer.MultiSelecao();
    multiSelecao.iniciar();
});
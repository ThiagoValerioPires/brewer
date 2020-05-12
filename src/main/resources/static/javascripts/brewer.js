var Brewer = Brewer || {};

Brewer.MaskMoney = (function(){

    function MaskMoney(){
        this.decimal = $(".js-decimal");
        this.plain = $(".js-plain");
    }

    MaskMoney.prototype.enable = function() {
        this.decimal.maskMoney({decimal: ',', thousands: '.'});
        this.plain.maskMoney({thousands: '.', precision: 0});
    }

    return MaskMoney;

}());

Brewer.MaskPhoneNumber = (function(){

    function MaskPhoneNumber() {
        this.inputPhoneNumber = $('.js-phone-number');
    }

    MaskPhoneNumber.prototype.enable = function() {
        let maskBehavior = function (val) {
            return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009'
        }

        let options = {
            onKeyPress: function (val, e, field, options) {
                field.mask(maskBehavior.apply({}, arguments), options);
            }
        };

        this.inputPhoneNumber.mask(maskBehavior, options);

    }

    return MaskPhoneNumber;

}());

Brewer.MaskCep = (function(){

    function MaskCep() {
        this.inputCep = $('.js-cep');
    }

    MaskCep.prototype.enable = function() {
        this.inputCep.mask('00000-000');
    }

    return MaskCep;

}());

Brewer.MaskDate = (function() {

    function MaskDate() {
        this.inputDate = $('.js-date');
    }

    MaskDate.prototype.enable = function() {
        this.inputDate.mask('00/00/0000');
        this.inputDate.datepicker({
            orientation: 'bottom',
            language: 'pt-BR',
            autoclose: true
        });
    }

    return MaskDate;

}());

Brewer.Security = (function() {

    function Security() {
        this.token = $('input[name=_csrf]').val();
        this.tokenHeader = $('input[name=_csrf_header]').val();
    }

    Security.prototype.enable = function() {
        $(document).ajaxSend(function(event, jqxhr, settings){
            jqxhr.setRequestHeader(this.tokenHeader,this.token);
        }.bind(this));
    }

    return Security;

}());


$(function(){
    let maskMoney = new Brewer.MaskMoney();
    maskMoney.enable();

    let maskPhoneNumber = new Brewer.MaskPhoneNumber();
    maskPhoneNumber.enable();

    let maskCep = new Brewer.MaskCep();
    maskCep.enable();

    let maskDate = new Brewer.MaskDate();
    maskDate.enable();

    let security = new Brewer.Security();
    security.enable();
});
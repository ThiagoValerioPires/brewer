package com.algaworks.brewer.validation.validator;

import com.algaworks.brewer.validation.AtributoConfirmacao;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object> {

    private String atributo;
    private String atributoConfirmacao;

    @Override
    public void initialize(AtributoConfirmacao constraintAnnotation) {
        this.atributo = constraintAnnotation.atributo();
        this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        boolean valido;

        try {
            Object valorAtributo = BeanUtils.getProperty(object, atributo);
            Object valorAtributoConfirmacao = BeanUtils.getProperty(object, atributoConfirmacao);
            valido =  (valorAtributo == null && valorAtributoConfirmacao ==null) || valorAtributo.equals(valorAtributoConfirmacao);
        }catch(Exception e){
            throw new RuntimeException("Erro recuperando valores dos atributos",e);
        }

        if(!valido){
            context.disableDefaultConstraintViolation();
            String mensagem = context.getDefaultConstraintMessageTemplate();
            ConstraintValidatorContext.ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate(mensagem);
            builder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
        }

        return valido;

    }
}

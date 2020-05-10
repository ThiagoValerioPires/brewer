package com.algaworks.brewer.model;

import com.algaworks.brewer.model.validation.ClienteGroupSequenceProvider;
import com.algaworks.brewer.model.validation.group.CnpjGroup;
import com.algaworks.brewer.model.validation.group.CpfGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cliente")
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Tipo Pessoa é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @CPF(groups = CpfGroup.class, message = "CPF inválido")
    @CNPJ(groups = CnpjGroup.class, message = "CNPJ inválido")
    @Column(name = "cpf_cnpj")
    private String cpfOuCnpj;

    private String telefone;

    @Email(message = "E-mail invalido")
    private String email;

    @Embedded
    private Endereco endereco;

    @PrePersist @PreUpdate
    private void prePersistPreUpdate(){
        this.cpfOuCnpj = getCpfOuCnpjSemFormatacao();
    }

    @PostLoad
    private void postLoad(){

    }

    public String getCpfOuCnpjSemFormatacao(){
        return TipoPessoa.removerFormatacao(this.cpfOuCnpj);
    }
}

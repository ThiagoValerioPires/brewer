package com.algaworks.brewer.model;

import com.algaworks.brewer.validation.AtributoConfirmacao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AtributoConfirmacao(atributo="senha", atributoConfirmacao = "confirmacaoSenha", message = "Confirmacao da senha não confere")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min=3, message = "O tamanho do nome nao deve ser menor que 3")
    private String nome;

    @NotBlank(message ="E-mail é obrigatório")
    @Email(message = "E-mail invalido")
    private String email;


    private String senha;

    @Transient
    private String confirmacaoSenha;

    private Boolean ativo;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(name="data_nascimento")
    private LocalDate dataNascimento;

    @Size(min = 1, message = "Selecione pelo menos um grupo")
    @ManyToMany
    @JoinTable(name = "usuario_grupo",
            joinColumns = @JoinColumn(name="codigo_usuario"),
            inverseJoinColumns = @JoinColumn(name="codigo_grupo")
    )
    private List<Grupo> grupos;

    public boolean isNovo(){
        return codigo == null;
    }

}

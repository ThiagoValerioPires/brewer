package com.algaworks.brewer.model;

import com.algaworks.brewer.validation.SKU;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="cerveja")
public class Cerveja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @SKU
    @NotBlank(message = "SKU é obrigatório")
    private String sku;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "A descrićão é obrigatória")
    @Size(max=50, message = "O Tamanho da descrićão deve ser maior que 50")
    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.00", message = "O valor da cerveja deve ser maior que R$0.00")
    @DecimalMax(value = "9999999.99", message = "O valor da cerveja deve ser menor que R$9.999.999,99")
    private BigDecimal valor;

    @NotNull(message = "O teor alcoólico é obrigatório")
    @DecimalMax(value = "100.00", message = "O teor alcoolico deve ser menor que 100")
    @Column(name="teor_alcoolico")
    private BigDecimal teorAlcoolico;

    @NotNull(message = "A comissao é obrigatória")
    @DecimalMax(value = "100.00", message = "A comissao deve ser menor que 100")
    private BigDecimal comissao;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Max(value = 9999, message = "A quantidade em estoque deve ser menor que 9.999")
    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque;

    @NotNull(message = "A origem é obrigatória")
    @Enumerated(EnumType.STRING)
    private Origem origem;

    @NotNull(message = "O sabor é obrigatório")
    @Enumerated(EnumType.STRING)
    private Sabor sabor;

    @NotNull(message = "O estilo é obrigatório")
    @ManyToOne
    @JoinColumn(name="codigo_estilo")
    private Estilo estilo;

    private String foto;

    @Column(name = "content_type")
    private String contentType;

    @PrePersist @PreUpdate
    private void prePersistUpdate(){
        sku = sku.toUpperCase();
    }

    public String getFotoOuMock(){
        return StringUtils.isEmpty(foto) ? "cerveja-mock.png" : foto;
    }

}

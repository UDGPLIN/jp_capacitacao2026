package br.com.indra.danilo_lins.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PRODUTOS")

public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    @Column(name = "codigo_barras")
    private String codigoBarras;

    @ManyToOne
    @JoinColumn (name = "categoria_id")
    private Categoria categoria;

    @JoinColumn (name = "quantity")
    private Integer quantity;

    public void setEstoque(Integer quantity) {

        this.quantity = quantity;
    }

    public Integer getEstoque() {
     return this.quantity;
    }
}
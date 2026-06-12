package com.web.mycar.SpringWeb.models;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // Importante
import jakarta.validation.constraints.NotNull;  // Importante
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Peca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "O nome é obrigatório") // Garante que não venha vazio ou só espaços
    private String nome;
    
    @NotBlank(message = "O código é obrigatório")
    private String codigo; // Mantemos String, mas agora obrigatória
    
    @NotBlank(message = "A marca é obrigatória")
    private String marca;

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
    @Column(nullable = false)
    @NotNull(message = "O preço de compra é obrigatório") // Para números usamos NotNull
    private Double precoCompra;

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
    @Column(nullable = false)
    @NotNull(message = "O preço de venda é obrigatório")
    private Double precoVenda;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @NumberFormat(pattern = "#,##0.00")
    private Integer quantidadeEstoque; // Se estoque puder ser vazio (null), deixe sem anotação. Se for obrigatório, use @NotNull.
}   
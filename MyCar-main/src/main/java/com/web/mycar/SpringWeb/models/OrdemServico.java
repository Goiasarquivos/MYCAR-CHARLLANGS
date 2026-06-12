package com.web.mycar.SpringWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPagamento;
    
    private String formaPagamento;
    
    @Column(nullable = false)
    private Double valorTotal; // Valor do serviço
    
    private Double desconto;
    
    @Column(nullable = false)
    private Double valorFinal; // valorTotal - desconto
    
    private String statusPagamento; // Pendente, Pago, Parcial
}
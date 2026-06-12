package com.web.mycar.SpringWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoPeca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peca_id", nullable = false)
    private Peca peca;
    
    @Column(nullable = false)
    private Integer quantidade;
    
    @Column(nullable = false)
    private Double valorUnitario; // Preço da peça no momento do serviço
    
    @Column(nullable = false)
    private Double valorTotal; // quantidade * valorUnitario
}

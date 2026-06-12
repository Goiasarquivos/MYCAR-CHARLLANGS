package com.web.mycar.SpringWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoMecanico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario mecanico;
    
    @Column(nullable = false)
    private Double horasTrabalhadas;
    
    @Column(nullable = false)
    private Double valorHora;
    
    @Column(nullable = false)
    private Double valorMaoDeObra; // horasTrabalhadas * valorHora
    
    @Column(nullable = false)
    private Double valorComissao; // Calculado com base no percentualComissao do mecânico
}
package com.web.mycar.SpringWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;

import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descricao;
    
    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntrada = new Date(); 
    
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
    @Column(nullable = false)
    private Double valorMaoDeObra = 0.0;
    
    private Double valorTotal;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    
   
    
    @ManyToOne
    @JoinColumn(name = "funcionario_id") // Cria uma coluna 'funcionario_id' na tabela servico
    private Funcionario mecanico; 

    

    // Relacionamento com as peças
    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoPeca> pecasUtilizadas = new ArrayList<>();
}
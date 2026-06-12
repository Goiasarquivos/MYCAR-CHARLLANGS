package com.web.mycar.SpringWeb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String cpf;
    
    private String cargo; // Mecânico, Gerente, Recepcionista, etc.
    
    private Double salarioBase;
    
    private Double percentualComissao; // Para mecânicos
}

package com.web.mycar.SpringWeb.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "veiculos")
public class Veiculo {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;
    
   
    // Validação da placa no formato mercosul ou antiga dados da internet
    @NotBlank(message = "A placa é obrigatória")
    // O regex abaixo aceita ABC1234 (Antiga) OU ABC1D23 (Mercosul)
    @Pattern(regexp = "[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}", message = "A placa deve seguir o padrão brasileiro (Ex: ABC-1234 ou ABC1C34)")
    @Column(nullable = false, unique = true)
    private String placa;
    // ---------------------------
    
    @NotBlank(message = "O modelo é obrigatório")
    @Column(nullable = false)
    private String modelo;
    
    @NotBlank(message = "A marca é obrigatória")
    private String marca;

    @NotNull(message = "O ano é obrigatório")
    @Column(nullable = false)
    @Min(value = 1886, message = "Ano inválido (mínimo 1886)")
    @Max(value = 2026, message = "Ano inválido (máximo 2026)") 
    private Integer ano;
    


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
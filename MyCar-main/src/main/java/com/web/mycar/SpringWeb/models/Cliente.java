package com.web.mycar.SpringWeb.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

   
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
 

    @NotBlank(message = "O telefone é obrigatório")
    // O Regex abaixo aceita: (11) 99999-9999 OU (11) 3333-4444
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Formato inválido. Use (XX) XXXXX-XXXX")
    @Column(name = "telefone", length = 15, nullable = false)
    private String telefone;

    

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Veiculo> veiculos = new ArrayList<>();
    
}

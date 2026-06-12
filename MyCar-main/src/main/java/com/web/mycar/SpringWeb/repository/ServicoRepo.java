package com.web.mycar.SpringWeb.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.web.mycar.SpringWeb.models.Servico;

public interface ServicoRepo extends JpaRepository<Servico, Long> {

    // Essa Query personalizada força o banco a trazer o Mecânico e o Veículo junto com o Serviço
    // Isso evita o problema de dados em branco na tabela
    @Query("SELECT s FROM Servico s LEFT JOIN FETCH s.mecanico LEFT JOIN FETCH s.veiculo LEFT JOIN FETCH s.veiculo.cliente")
    List<Servico> findAllCompleto();
}
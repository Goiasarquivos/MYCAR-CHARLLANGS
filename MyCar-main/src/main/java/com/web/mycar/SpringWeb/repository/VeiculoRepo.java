package com.web.mycar.SpringWeb.repository;

import com.web.mycar.SpringWeb.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRepo extends JpaRepository<Veiculo, Long> {

    // Buscar todos os veículos de um cliente específico
    List<Veiculo> findByClienteId(Long clienteId);

    // Verificar se já existe veículo com a placa
    boolean existsByPlaca(String placa);

    // Buscar veículo pela placa
    Optional<Veiculo> findByPlaca(String placa);

   
}
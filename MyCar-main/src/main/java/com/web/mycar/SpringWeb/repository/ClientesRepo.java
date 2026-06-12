package com.web.mycar.SpringWeb.repository;

import com.web.mycar.SpringWeb.models.Cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientesRepo extends JpaRepository<Cliente, Long> {

    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.veiculos")
    List<Cliente> findAllWithVeiculos();


    
}

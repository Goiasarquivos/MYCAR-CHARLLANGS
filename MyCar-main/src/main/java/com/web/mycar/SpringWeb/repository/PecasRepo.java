package com.web.mycar.SpringWeb.repository;

import com.web.mycar.SpringWeb.models.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PecasRepo extends JpaRepository<Peca, Long> {
    
    // Não precisa declarar o findAll() aqui!
    // Ele já existe automaticamente por causa do "extends JpaRepository".
    
}
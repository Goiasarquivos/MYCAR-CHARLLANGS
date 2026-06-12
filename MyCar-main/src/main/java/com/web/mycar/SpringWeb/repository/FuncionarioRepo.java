package com.web.mycar.SpringWeb.repository;
import com.web.mycar.SpringWeb.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepo extends JpaRepository<Funcionario, Long> {
    
    // Não precisa declarar o findAll() aqui!
    // Ele já existe automaticamente por causa do "extends JpaRepository".
    
}
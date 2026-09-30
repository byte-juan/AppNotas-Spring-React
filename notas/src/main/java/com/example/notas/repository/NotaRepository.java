package com.example.notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 

public interface NotaRepository extends JpaRepository<com.example.notas.model.Nota, Long> {  
}

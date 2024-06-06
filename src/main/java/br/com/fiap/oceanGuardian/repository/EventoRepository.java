package br.com.fiap.oceanGuardian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.oceanGuardian.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer>{
    
}

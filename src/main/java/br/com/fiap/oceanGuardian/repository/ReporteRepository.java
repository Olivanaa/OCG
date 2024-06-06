package br.com.fiap.oceanGuardian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.oceanGuardian.model.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Integer> {
    
}

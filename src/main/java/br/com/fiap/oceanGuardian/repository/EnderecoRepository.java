package br.com.fiap.oceanGuardian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.oceanGuardian.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    
}

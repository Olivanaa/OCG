package br.com.fiap.oceanGuardian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.oceanGuardian.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    Usuario findByEmail(String email);
    
}

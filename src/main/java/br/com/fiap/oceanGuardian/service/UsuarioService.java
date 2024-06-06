package br.com.fiap.oceanGuardian.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.oceanGuardian.model.Usuario;
import br.com.fiap.oceanGuardian.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class UsuarioService {


    @Autowired
    UsuarioRepository repository;

    
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario criar(@Valid Usuario usuario) {
  
        return repository.save(usuario);
    }

    public List<Usuario> buscarTodas() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "Usuario não encontrado"));
    }

    public void deletar(Integer id) {
        verificar(id);
        repository.deleteById(id);
    }

    public Usuario atualizar(Integer id, Usuario usuario) {
        verificar(id);
        usuario.setId(id);
        return repository.save(usuario);
    }

    public Integer authenticate(String email, String senha){
        Usuario usuario = repository.findByEmail(email);
        if(usuario != null && senha.equals(usuario.getSenha())){
            return usuario.getId();
        }
        return null;
    }

    private void verificar(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe usuario com o id informado."));
    }
    
}


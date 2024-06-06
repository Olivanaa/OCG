package br.com.fiap.oceanGuardian.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.oceanGuardian.model.Evento;
import br.com.fiap.oceanGuardian.repository.EventoRepository;
import jakarta.validation.Valid;

@Service
public class EventoService {

    @Autowired
    EventoRepository repository;

    public EventoService(EventoRepository repository) {
        this.repository = repository;
    }

    public Evento criar(@Valid Evento projeto) {
        return repository.save(projeto);
    }

    public List<Evento> buscarTodas() {
        return repository.findAll();
    }

    public Evento buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "Evento não encontrado"));
    }

    public void deletar(Integer id) {
        verificar(id);
        repository.deleteById(id);
    }

    public Evento atualizar(Integer id, Evento projeto) {
        verificar(id);
        projeto.setId(id);
        return repository.save(projeto);
    }

    private void verificar(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe evento com o id informado."));
    }
    
}
    


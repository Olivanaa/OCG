package br.com.fiap.oceanGuardian.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.oceanGuardian.model.Endereco;
import br.com.fiap.oceanGuardian.repository.EnderecoRepository;
import jakarta.validation.Valid;


@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository repository;


    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public Endereco criar(@Valid Endereco endereco) {
         return repository.save(endereco);

    }

    public List<Endereco> buscarTodas() {
        return repository.findAll();
    }

    public Endereco buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "Endereço não encontrado"));
    }

    public void deletar(Integer id) {
        verificar(id);
        repository.deleteById(id);
    }

    public Endereco atualizar(Integer id, Endereco endereco) {
        verificar(id);
        endereco.setId(id);
        return repository.save(endereco);
    }

    private void verificar(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe endereço com o id informado."));
    }

}

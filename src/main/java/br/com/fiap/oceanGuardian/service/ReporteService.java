package br.com.fiap.oceanGuardian.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.oceanGuardian.model.Reporte;
import br.com.fiap.oceanGuardian.repository.ReporteRepository;
import jakarta.validation.Valid;

@Service
public class ReporteService {


    @Autowired
    ReporteRepository repository;

    public ReporteService(ReporteRepository repository) {
        this.repository = repository;
    }

    public Reporte criar(@Valid Reporte reporte) {
        return repository.save(reporte);
    }

    public List<Reporte> buscarTodas() {
        return repository.findAll();
    }

    public Reporte buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "Reporte não encontrado"));
    }

    public void deletar(Integer id) {
        verificar(id);
        repository.deleteById(id);
    }

    public Reporte atualizar(Integer id, Reporte reporte) {
        verificar(id);
        reporte.setId(id);
        return repository.save(reporte);
    }

    private void verificar(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe reporte com o id informado."));
    }
    
}

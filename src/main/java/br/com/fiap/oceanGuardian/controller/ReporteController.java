package br.com.fiap.oceanGuardian.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.oceanGuardian.model.Reporte;
import br.com.fiap.oceanGuardian.repository.ReporteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("reporte")
@Tag(name = "Reporte")
public class ReporteController {

    @Autowired
    ReporteRepository repository;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Cadastrar reporte", description = "Cria um novo cadastro de reporte com dados enviados no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "reporte cadastrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição", content = @Content)
    })
    public ResponseEntity<Reporte> create(@RequestBody @Valid Reporte reporte) {
        repository.save(reporte);

        return ResponseEntity
                .created(reporte.toEntityModel().getRequiredLink("self").toUri())
                .body(reporte);
    }

    @GetMapping
    @Operation(summary = "Listar todos as reportes cadastrados", description = "Retorna um array com todos os reportes em formato de objeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes foi retornada com sucesso!"),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content),

    })
    public List<Reporte> index() {
        return repository.findAll();

    }

    @GetMapping("{id}")
    @Operation(summary = "Listar o reporte com o parametro id", description = "Retornar os detalhes do reporte com o id informado como parâmetro de path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados do reporte retornados com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Não existe dados do reporte com o id informado.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public EntityModel<Reporte> show(@PathVariable Integer id) {
        var reporte = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("reporte não encontrada"));

        return reporte.toEntityModel();
    }

    @PutMapping("{id}")
    @Operation(summary = "Alterar o reporte com o parametro id", description = "Altera os dados do reporte especificado pelo id, utilizando as informações enviadas no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "reporte cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public EntityModel<Reporte> update(@PathVariable Integer id, @RequestBody Reporte reporte) {

        repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(NOT_FOUND, "Não existe endereço com o id informado."));
        reporte.setId(id);
        repository.save(reporte);

        return reporte.toEntityModel();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletar o reporte com o parametro id", description = "Apaga os dados do reporte com o id especificado no parâmetro de path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados do reporte foram apagados com sucesso!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Não existe dados do reporte com o id informado.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public ResponseEntity<Object> destroy(@PathVariable Integer id) {
        repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("reporte não encontrada"));

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

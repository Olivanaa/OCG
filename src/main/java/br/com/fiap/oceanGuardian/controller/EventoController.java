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

import br.com.fiap.oceanGuardian.model.Evento;
import br.com.fiap.oceanGuardian.repository.EventoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("evento")
@Tag(name = "Evento")
public class EventoController {

    @Autowired
    EventoRepository repository;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Cadastrar evento", description = "Cria um novo cadastro de evento com dados enviados no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição", content = @Content)
    })
    public ResponseEntity<Evento> create(@RequestBody @Valid Evento evento) {
        repository.save(evento);

        return ResponseEntity
                .created(evento.toEntityModel().getRequiredLink("self").toUri())
                .body(evento);
    }

    @GetMapping
    @Operation(summary = "Listar todos os eventos cadastrados", description = "Retorna um array com todos os eventos em formato de objeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos foi retornada com sucesso!"),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content),

    })
    public List<Evento> index() {
        return repository.findAll();

    }

    @GetMapping("{id}")
    @Operation(summary = "Listar o evento com o parametro id", description = "Retornar os detalhes do evento com o id informado como parâmetro de path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados do evento retornados com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Não existe dados do evento com o id informado.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public EntityModel<Evento> show(@PathVariable Integer id) {
        var evento = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("evento não encontrada"));

        return evento.toEntityModel();
    }

    @PutMapping("{id}")
    @Operation(summary = "Alterar o evento com o parametro id", description = "Altera os dados do evento especificado pelo id, utilizando as informações enviadas no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public EntityModel<Evento> update(@PathVariable Integer id, @RequestBody Evento evento) {

        repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(NOT_FOUND, "Não existe endereço com o id informado."));
        evento.setId(id);
        repository.save(evento);

        return evento.toEntityModel();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletar o evento com o parametro id", description = "Apaga os dados do evento com o id especificado no parâmetro de path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados do evento foram apagados com sucesso!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Não existe dados do evento com o id informado.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public ResponseEntity<Object> destroy(@PathVariable Integer id) {
        repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("usuario não encontrada"));

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

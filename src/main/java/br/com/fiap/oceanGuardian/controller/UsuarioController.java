package br.com.fiap.oceanGuardian.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.oceanGuardian.model.Usuario;
import br.com.fiap.oceanGuardian.repository.UsuarioRepository;
import br.com.fiap.oceanGuardian.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("usuario")
@Tag(name = "Usuario")
@Slf4j
public class UsuarioController {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Cadastrar usuario", description = "Cria um novo cadastro de usuario com dados enviados no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "usuario cadastrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição", content = @Content)
    })
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario usuario) {
        repository.save(usuario);

        return ResponseEntity
                .created(usuario.toEntityModel().getRequiredLink("self").toUri())
                .body(usuario);
    }

    @GetMapping
    @Operation(summary = "Listar todos as usuarios cadastrados", description = "Retorna um array com todos os usuarios em formato de objeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios foi retornada com sucesso!"),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content),

    })
    public List<Usuario> index() {
        return service.buscarTodas();

    }

    @GetMapping("{id}")
    @Operation(summary = "Listar o usuario com o parametro id", description = "Retornar os detalhes do usuario com o id informado como parâmetro de path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados do usuario retornados com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Não existe dados do usuario com o id informado.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public EntityModel<Usuario> show(@PathVariable Integer id) {
        log.info("buscando usuario com id {}", id);

        var usuario = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("movimentação não encontrada"));

        return usuario.toEntityModel();
    }

    @PutMapping("{id}")
    @Operation(summary = "Alterar o usuario com o parametro id", description = "Altera os dados do usuario especificado pelo id, utilizando as informações enviadas no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public EntityModel<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuario) {

        repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(NOT_FOUND, "Não existe endereço com o id informado."));
        usuario.setId(id);
        repository.save(usuario);

        return usuario.toEntityModel();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletar o usuario com o parametro id", description = "Apaga os dados do usuario com o id especificado no parâmetro de path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados do usuario foram apagados com sucesso!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Não existe dados do usuario com o id informado.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public ResponseEntity<Object> destroy(@PathVariable Integer id) {
        repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("usuario não encontrado"));

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/login")
    @Operation(summary = "Deletar o usuario com o parametro id", description = "Apaga os dados do usuario com o id especificado no parâmetro de path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados do usuario foram apagados com sucesso!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Não existe dados do usuario com o id informado.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public ResponseEntity<Integer> login(@RequestParam("email") String email, @RequestParam("senha") String senha) {
        Integer usuarioId = service.authenticate(email, senha);

        if (usuarioId != null) {
            return ResponseEntity.ok().body(usuarioId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}

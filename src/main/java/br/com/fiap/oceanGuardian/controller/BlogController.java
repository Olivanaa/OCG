package br.com.fiap.oceanGuardian.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.oceanGuardian.model.Blog;
import br.com.fiap.oceanGuardian.repository.BlogRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("blog")
@Tag(name = "Blog")
@CacheConfig(cacheNames = "blog")
public class BlogController {

    @Autowired
    BlogRepository repository;

    @Autowired
    PagedResourcesAssembler<Blog> pageAssembler;

    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar post", description = "Cria um novo cadastro de post com dados enviados no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição", content = @Content)
    })
    public ResponseEntity<Blog> create(@RequestBody @Valid Blog blog) {
        repository.save(blog);
        return ResponseEntity
                .created(blog.toEntityModel().getRequiredLink("self").toUri())
                .body(blog);
    }

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todos os posts cadastrados", description = "Retorna uma página com todos os posts em formato de objeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de posts foi retornada com sucesso!"),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content),

    })
    public PagedModel<EntityModel<Blog>> index(
            @RequestParam(required = false) Integer mes,
            @PageableDefault(sort = "data", direction = Direction.ASC) Pageable pageable) {

        Page<Blog> page = null;

        if (mes != null) {
            page = repository.findByMes(mes, pageable);
        }

        if (page == null) {
            page = repository.findAll(pageable);
        }

        return pageAssembler.toModel(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar o post com o parametro id", description = "Retornar os detalhes do post com o id informado como parâmetro de path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados do post retornados com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Não existe dados do post com o id informado.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public EntityModel<Blog> show(@PathVariable Integer id) {
        var blog = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("post não encontrado"));
        return blog.toEntityModel();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar o post com o parametro id", description = "Apaga os dados do post com o id especificado no parâmetro de path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados do post foram apagados com sucesso!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Não existe dados do post com o id informado.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acesso não permitido. É necessário autentificação.", content = @Content)
    })
    public ResponseEntity<Object> destroy(@PathVariable Integer id) {
        repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("post não encontrado"));
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

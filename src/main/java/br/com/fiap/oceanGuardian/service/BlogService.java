package br.com.fiap.oceanGuardian.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.oceanGuardian.model.Blog;
import br.com.fiap.oceanGuardian.repository.BlogRepository;
import jakarta.validation.Valid;

@Service
public class BlogService {
    
    @Autowired
    BlogRepository repository;

    
    public BlogService(BlogRepository repository) {
        this.repository = repository;
    }

    public Blog criar(@Valid Blog blog) {
        return repository.save(blog);
    }

    public Page<Blog> buscarTodas(Integer mes, Pageable pageable) {
        if (mes != null) {
            return repository.findByMes(mes, pageable);
        }
        return repository.findAll(pageable);
    }

    public Blog buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "Post não encontrado"));
    }

    public void deletar(Integer id) {
        verificar(id);
        repository.deleteById(id);
    }

    public Blog atualizar(Integer id, Blog blog) {
        verificar(id);
        blog.setId(id);
        return repository.save(blog);
    }

    private void verificar(Integer id) {
        repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "Não existe post com o id informado."));
    } 

}

package br.com.fiap.oceanGuardian.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.oceanGuardian.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    @Query("SELECT m FROM Blog m WHERE MONTH(m.data) = :mes")
    Page<Blog> findByMes(Integer mes, Pageable pageable);
    
}

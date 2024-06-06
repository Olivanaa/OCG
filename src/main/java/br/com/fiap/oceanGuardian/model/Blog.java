package br.com.fiap.oceanGuardian.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fiap.oceanGuardian.controller.BlogController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Blog extends EntityModel<Blog>{
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "A data não pode ser nula.")
    private LocalDate data;

    @NotBlank(message = "O título não pode estar em branco.")
    private String titulo;

    @Lob
    private String artigo;


    @ManyToOne
    private Usuario autor;

    public EntityModel<Blog> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(BlogController.class).show(id)).withSelfRel(),
            linkTo(methodOn(BlogController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(BlogController.class).index(null, null)).withRel("contents")
        );

    }
    
}

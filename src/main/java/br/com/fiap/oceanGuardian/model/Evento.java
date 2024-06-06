package br.com.fiap.oceanGuardian.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fiap.oceanGuardian.controller.EventoController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class Evento extends EntityModel<Evento> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @NotBlank(message = "A descrição não pode estar em branco.")
    @Size(min = 5, max = 500, message = "A descrição deve ter entre 5 e 500 caracteres.")
    private String descricao;

    @NotBlank(message = "A localização não pode estar em branco.")
    private String localizacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    private String doacoes;

    @ManyToOne
    private Usuario autor;

    public EntityModel<Evento> toEntityModel() {
        return EntityModel.of(
            this, 
            linkTo(methodOn(EventoController.class).show(id)).withSelfRel(),
            linkTo(methodOn(EventoController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(EventoController.class).index()).withRel("contents")
        );

    }

}

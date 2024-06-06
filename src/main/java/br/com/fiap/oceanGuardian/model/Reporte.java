package br.com.fiap.oceanGuardian.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fiap.oceanGuardian.controller.ReporteController;
import br.com.fiap.oceanGuardian.validation.NivelGravidade;
import br.com.fiap.oceanGuardian.validation.TipoPoluicao;
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
public class Reporte extends EntityModel<Reporte> {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "A descrição não pode estar em branco.")
    @Size(min = 5, max = 500, message = "A descrição deve ter entre 5 e 500 caracteres.")
    private String descricao;

    @TipoPoluicao
    private String tipo;

    @NivelGravidade
    private String nivel;
    
    private double latitude;
    private double longitude;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;   
    
    @ManyToOne
    private Usuario usuario;

        public EntityModel<Reporte> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(ReporteController.class).show(id)).withSelfRel(),
            linkTo(methodOn(ReporteController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(ReporteController.class).index()).withRel("contents")
        );

    }
    
}

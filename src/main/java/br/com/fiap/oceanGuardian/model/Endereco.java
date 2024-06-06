package br.com.fiap.oceanGuardian.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;

import br.com.fiap.oceanGuardian.controller.EnderecoController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Endereco extends EntityModel<Endereco> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "O logradouro não pode estar em branco.")
    private String logradouro;

    @NotBlank(message = "O número não pode estar em branco.")
    private String numero;

    private String complemento;

    @NotBlank(message = "O bairro não pode estar em branco.")
    private String bairro;

    @NotBlank(message = "A cidade não pode estar em branco.")
    private String cidade;

    @NotBlank(message = "O estado não pode estar em branco.")
    private String estado;

    @NotBlank
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 caracteres.")
    private String cep;

    private Integer userId;

    public EntityModel<Endereco> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(EnderecoController.class).show(id)).withSelfRel(),
            linkTo(methodOn(EnderecoController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(EnderecoController.class).index()).withRel("contents")
        );

    }

}

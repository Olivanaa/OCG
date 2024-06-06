package br.com.fiap.oceanGuardian.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;

import br.com.fiap.oceanGuardian.controller.UsuarioController;
import br.com.fiap.oceanGuardian.validation.TipoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
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
public class Usuario extends EntityModel<Usuario> {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O telefone não pode estar em branco.")
    @Size(min = 10, max = 11, message = "O telefone deve ter entre 10 e 11 caracteres.")
    private String telefone;
    
    @Email(message = "O email deve ser válido.")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 6, max = 8, message = "A senha deve ter entre 6 e 8 caracteres.")
    private String senha;

    @TipoUsuario
    private String tipo;

        public EntityModel<Usuario> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(UsuarioController.class).show(id)).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(UsuarioController.class).index()).withRel("contents")
        );

    }


    
}

package vitalabs.com.clinica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import vitalabs.com.clinica.controller.ConsultaController;

import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Secretaria extends AbstractEntity {
    @CreationTimestamp
    Date dataHoraCriacao;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Data
    public static class DtoRequest{
        String nome;
        String contato;
        String email;
        String cpf;
        String role = "SECRETARIA";

        public static Secretaria convertToEntity(Secretaria.DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Secretaria.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<Secretaria.DtoResponse> {
        String nome;
        String contato;
        String email;
        String role;

        public static Secretaria.DtoResponse convertToDto(Object p, ModelMapper mapper){
            return mapper.map(p, Secretaria.DtoResponse.class);
        }

        public void generateLinks(String id){
            add(linkTo(ConsultaController.class).slash(id).withSelfRel());
            add(linkTo(ConsultaController.class).withRel("Secretaria"));
            add(linkTo(ConsultaController.class).slash(id).withRel("delete"));
        }
    }
}

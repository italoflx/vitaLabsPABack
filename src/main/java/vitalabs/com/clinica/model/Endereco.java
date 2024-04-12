package vitalabs.com.clinica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Data
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String rua;
    Integer numero;
    String complemento;
    String cidade;
    String estado;

    @CreationTimestamp
    Date dataHoraCriacao;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Data
    public static class DtoRequest{
        String rua;
        Integer numero;
        String complemento;
        String cidade;
        String estado;

        public static Endereco convertToEntity(Endereco.DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Endereco.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<Endereco.DtoResponse> {
        String rua;
        Integer numero;
        String complemento;
        String cidade;
        String estado;
        public static Endereco.DtoResponse convertToDto(Object p, ModelMapper mapper){
            return mapper.map(p, Endereco.DtoResponse.class);
        }

        public void generateLinks(String id){
            add(linkTo(ConsultaController.class).slash(id).withSelfRel());
            add(linkTo(ConsultaController.class).withRel("Endereco"));
            add(linkTo(ConsultaController.class).slash(id).withRel("delete"));
        }
    }
}

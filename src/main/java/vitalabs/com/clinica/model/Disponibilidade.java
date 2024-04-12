package vitalabs.com.clinica.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import vitalabs.com.clinica.controller.PacienteController;

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Entity
@Data
public class Disponibilidade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Date data;
    String hora;
    String local;
    String status;

    @Data
    public static class DtoRequest{
        String local;
        String data;
        String hora;
        public static Disponibilidade convertToEntity(Disponibilidade.DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Disponibilidade.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<Disponibilidade.DtoResponse> {
        String local;
        String data;
        String hora;
        public static Disponibilidade.DtoResponse convertToDto(Disponibilidade p, ModelMapper mapper){
            return mapper.map(p, Disponibilidade.DtoResponse.class);
        }

        public void generateLinks(String id){
            add(linkTo(Disponibilidade.class).slash(id).withSelfRel());
            add(linkTo(Disponibilidade.class).withRel("Disponibilidade"));
            add(linkTo(Disponibilidade.class).slash(id).withRel("delete"));
        }
    }
}

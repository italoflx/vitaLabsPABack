package vitalabs.com.clinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE disponibilidade SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Disponibilidade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Date data;
    String hora;
    Boolean status;

    LocalDateTime deletedAt;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Data
    public static class DtoRequest{
        String data;
        String hora;
        Boolean status = true;
        public static Disponibilidade convertToEntity(Disponibilidade.DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Disponibilidade.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<Disponibilidade.DtoResponse> {
        String id;
        String data;
        String hora;
        Boolean status;
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

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
import vitalabs.com.clinica.controller.PacienteController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE medico SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Medico extends AbstractEntity{
    String crm;
    String especialidade;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval=true)
    @JoinColumn(name="id_medico")
    List<Consulta> consultas = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "medico_disponibilidade",
            joinColumns = {
                @JoinColumn(name = "medico_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "disponibilidade_id")
            }
    )
    List<Disponibilidade> disponibilidades = new ArrayList<>();

    @Data
    public static class DtoRequest{
        String nome;
        String contato;
        String email;
        String crm;
        String especialidade;

        List<Disponibilidade> disponibilidades = new ArrayList<>();
        List<Consulta> consultas = new ArrayList<>();
        public static Medico convertToEntity(Medico.DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Medico.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String id;
        String nome;
        String contato;
        String email;
        String crm;
        String especialidade;
        String role;

        public static Medico.DtoResponse convertToDto(Medico p, ModelMapper mapper){
            return mapper.map(p, Medico.DtoResponse.class);
        }

        public void generateLinks(String id){
            add(linkTo(Medico.class).slash(id).withSelfRel());
            add(linkTo(Medico.class).withRel("Medico"));
            add(linkTo(Medico.class).slash(id).withRel("delete"));
        }
    }
}

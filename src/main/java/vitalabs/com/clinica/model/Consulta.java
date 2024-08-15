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
import vitalabs.com.clinica.controller.ConsultaController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE consulta SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Consulta{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String queixas;
    String diagnosticos;
    String procedimentos;
    String prescricoes;
    String observacoes;


    LocalDateTime deletedAt;
    @CreationTimestamp
    LocalDateTime dataHoraConsulta;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "disponibilidade_id")
    Disponibilidade disponibilidade;

    @Data
    public static class DtoRequest {
        String queixas;
        String diagnosticos;
        String procedimentos;
        String prescricoes;
        String observacoes;
        String medico_id;
        String paciente_id;
        String dataHoraConsulta;

        public static Consulta convertToEntity(Consulta.DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Consulta.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<Consulta.DtoResponse> {
        String id;
        String data;
        String hora;
        Medico medico;
        Paciente paciente;
        Disponibilidade disponibilidade;
        public static Consulta.DtoResponse convertToDto(Object p, ModelMapper mapper){
            return mapper.map(p, Consulta.DtoResponse.class);
        }

        public void generateLinks(String id){
            add(linkTo(ConsultaController.class).slash(id).withSelfRel());
            add(linkTo(ConsultaController.class).withRel("Consulta"));
            add(linkTo(ConsultaController.class).slash(id).withRel("delete"));
        }
    }
}

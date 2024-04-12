package vitalabs.com.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitalabs.com.clinica.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, String> {
}

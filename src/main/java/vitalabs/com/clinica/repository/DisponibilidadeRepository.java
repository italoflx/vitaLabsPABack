package vitalabs.com.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitalabs.com.clinica.model.Disponibilidade;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, String> {
}

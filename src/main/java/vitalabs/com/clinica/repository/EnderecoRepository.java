package vitalabs.com.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitalabs.com.clinica.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, String> {
}

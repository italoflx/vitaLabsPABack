package vitalabs.com.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitalabs.com.clinica.model.AbstractEntity;


public interface IGenericRepository<E extends AbstractEntity> extends JpaRepository<E, String> {
}

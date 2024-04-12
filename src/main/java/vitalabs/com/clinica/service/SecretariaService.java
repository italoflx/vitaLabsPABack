package vitalabs.com.clinica.service;

import org.springframework.stereotype.Service;
import vitalabs.com.clinica.model.Secretaria;
import vitalabs.com.clinica.repository.SecretariaRepository;

@Service
public class SecretariaService extends GenericService<Secretaria, SecretariaRepository>{
    SecretariaRepository repository;
    public SecretariaService(SecretariaRepository repository){
        super(repository);
    }

}

package vitalabs.com.clinica.service;

import org.springframework.stereotype.Service;
import vitalabs.com.clinica.model.Medico;
import vitalabs.com.clinica.repository.MedicoRepository;

@Service
public class MedicoService extends GenericService<Medico, MedicoRepository>{
    public MedicoService(MedicoRepository repository) {
        super(repository);
    }
}

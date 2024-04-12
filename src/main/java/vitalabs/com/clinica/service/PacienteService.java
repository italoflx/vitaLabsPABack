package vitalabs.com.clinica.service;

import org.springframework.stereotype.Service;
import vitalabs.com.clinica.model.Paciente;
import vitalabs.com.clinica.repository.PacienteRepository;

@Service
public class PacienteService extends GenericService<Paciente, PacienteRepository>{
    PacienteRepository repository;
    public PacienteService(PacienteRepository repository){
        super(repository);
    }
}

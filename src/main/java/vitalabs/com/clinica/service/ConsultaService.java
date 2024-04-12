package vitalabs.com.clinica.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vitalabs.com.clinica.model.Consulta;
import vitalabs.com.clinica.model.Medico;
import vitalabs.com.clinica.model.Paciente;
import vitalabs.com.clinica.repository.ConsultaRepository;
import vitalabs.com.clinica.repository.MedicoRepository;
import vitalabs.com.clinica.repository.PacienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService{
    ConsultaRepository repository;
    MedicoRepository medicoRepository;
    PacienteRepository pacienteRepository;
    ModelMapper mapper;

    public ConsultaService(ConsultaRepository repository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, ModelMapper mapper){
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public Consulta create(Consulta.DtoRequest c){
        Optional<Medico> medico = this.medicoRepository.findById(c.getMedico_id());
        Optional<Paciente> paciente = this.pacienteRepository.findById(c.getPaciente_id());

        if(medico.isPresent() && paciente.isPresent()){
            Consulta consulta = Consulta.DtoRequest.convertToEntity(c, this.mapper);
            consulta.setMedico(medico.get());
            consulta.setPaciente(paciente.get());

            return create(consulta);
        }else{
            throw new EntityNotFoundException();
        }
    }

    public Consulta create(Consulta c){
        return this.repository.save(c);
    }

    public List<Consulta> list(){
        return (List<Consulta>) this.repository.findAll();
    }

    public Consulta findById(String id){
        Optional<Consulta> consulta = this.repository.findById(id);
        if(consulta.isPresent())
            return consulta.get();
        else
            throw new EntityNotFoundException();
    }

    public Consulta update(Consulta c, String id){
        Consulta consulta = findById(id);
        return this.repository.save(c);
    }

    public void delete(String id){
        this.repository.delete(
            findById(id)
        );
    }
}
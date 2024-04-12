package vitalabs.com.clinica.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import vitalabs.com.clinica.model.Disponibilidade;
import vitalabs.com.clinica.repository.DisponibilidadeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DisponibilidadeService{

    DisponibilidadeRepository repository;
    public DisponibilidadeService(DisponibilidadeRepository repository){
        this.repository = repository;
    }

    public Disponibilidade create(Disponibilidade d) {
        return (Disponibilidade) this.repository.save(d);
    }

    public Disponibilidade update(Disponibilidade d, String id) {
        Optional<Disponibilidade> pessoaBanco = repository.findById(id);
        if (pessoaBanco.isPresent()){
            return (Disponibilidade) this.repository.save(d);
        }else{
            throw  new EntityNotFoundException();
        }
    }


    public void delete(String id) {
        this.repository.deleteById(id);
    }


    public List<Disponibilidade> list() {
        return (List<Disponibilidade>) this.repository.findAll();
    }


    public Disponibilidade getById(String id) {
        Optional<Disponibilidade> pessoaBanco = repository.findById(id);
        if (pessoaBanco.isPresent()){
            return (Disponibilidade) pessoaBanco.get();
        }else{
            throw  new EntityNotFoundException();
        }
    }
}

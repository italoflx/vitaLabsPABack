package vitalabs.com.clinica.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import vitalabs.com.clinica.model.Endereco;
import vitalabs.com.clinica.repository.EnderecoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository){
        this.repository = repository;
    }

    public Endereco create(Endereco e) {
        return (Endereco) this.repository.saveAndFlush(e);
    }

    public Endereco update(Endereco e, String id) {
        Optional<Endereco> pessoaBanco = repository.findById(id);
        if (pessoaBanco.isPresent()){
            return (Endereco) this.repository.save(e);
        }else{
            throw  new EntityNotFoundException();
        }
    }

    public void delete(String id) {
        this.repository.deleteById(id);
    }

    public List<Endereco> list() {
        return (List<Endereco>) this.repository.findAll();
    }

    public Endereco getById(String id) {
        Optional<Endereco> pessoaBanco = repository.findById(id);
        if (pessoaBanco.isPresent()){
            return (Endereco) pessoaBanco.get();
        }else{
            throw  new EntityNotFoundException();
        }
    }

}

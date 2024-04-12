package vitalabs.com.clinica.service;

import jakarta.persistence.EntityNotFoundException;
import vitalabs.com.clinica.model.AbstractEntity;
import vitalabs.com.clinica.repository.IGenericRepository;
import java.util.List;
import java.util.Optional;

public abstract class GenericService<E extends AbstractEntity, R extends IGenericRepository> implements IGenericService<E>{
    R repository;

    public GenericService(R repository){
        this.repository = repository;
    }

    @Override
    public E create(E e) {
        return (E) this.repository.saveAndFlush(e);
    }

    @Override
    public E update(E e, String id) {
        Optional<E> pessoaBanco = repository.findById(id);
        if (pessoaBanco.isPresent()){
            return (E) this.repository.save(e);
        }else{
            throw  new EntityNotFoundException();
        }
    }

    @Override
    public void delete(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public List<E> list() {
        return (List<E>) this.repository.findAll();
    }

    @Override
    public E getById(String id) {
        Optional<E> pessoaBanco = repository.findById(id);
        if (pessoaBanco.isPresent()){
            return (E) pessoaBanco.get();
        }else{
            throw  new EntityNotFoundException();
        }
    }
}

package vitalabs.com.clinica.service;

import vitalabs.com.clinica.model.AbstractEntity;
import java.util.List;

public interface IGenericService<E extends AbstractEntity>{
    public E create(E e);
    public E update(E e, String id);
    public void delete(String id);
    public List<E> list();
    public E getById(String id);
}

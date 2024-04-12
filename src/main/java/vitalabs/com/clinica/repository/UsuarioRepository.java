package vitalabs.com.clinica.repository;


import org.springframework.security.core.userdetails.UserDetails;
import vitalabs.com.clinica.model.Usuario;


public interface UsuarioRepository extends IGenericRepository<Usuario> {
    UserDetails findByUsername(String username);
}

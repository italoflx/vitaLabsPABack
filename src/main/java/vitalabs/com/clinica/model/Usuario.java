package vitalabs.com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vitalabs.com.clinica.controller.UsuarioController;

import java.util.Collection;
import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE usuario SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Usuario extends AbstractEntity implements UserDetails {
    private String username;
    @Column(unique = true)
    private String login;
    private String password;
    private Boolean isAdmin = false;
    private String role;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Usuário com nome em branco")
        String username;
        @NotBlank(message = "Login com nome em branco")
        String login;
        @NotBlank(message = "Password com nome em branco")
        String password;
        String nome;
        String contato;
        String email;
        String role;
        String cpf;
        public static Usuario convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Usuario.class);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String username;
        String login;
        String password;
        String role;

        public static DtoResponse convertToDto(Usuario u, ModelMapper mapper){
            return mapper.map(u, DtoResponse.class);
        }

        public void generateLinks(String id){
            add(linkTo(UsuarioController.class).slash(id).withSelfRel());
            add(linkTo(UsuarioController.class).withRel("usuarios"));
            add(linkTo(UsuarioController.class).slash(id).withRel("delete"));
        }
    }
}
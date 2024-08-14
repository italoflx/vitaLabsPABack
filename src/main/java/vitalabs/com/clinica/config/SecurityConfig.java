package vitalabs.com.clinica.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final RsaKeyProperties rsaKeys;
    private final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/api/public/**",
            "/api/public/authenticate",
            "/actuator/*",
            "/swagger-ui/**",
            "/login"
    };

    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/login").permitAll();

                    auth.requestMatchers(HttpMethod.POST, "/login/verificarToken").permitAll();
                    //inicialmente permitindo todas as consultas, para testes
                    auth.requestMatchers(HttpMethod.GET, "/pacientes").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/pacientes").permitAll();
//
//                    auth.requestMatchers(HttpMethod.GET, "/pacientes").hasRole("MEDICO");
//                    auth.requestMatchers(HttpMethod.POST, "/pacientes").hasRole("MEDICO");
//                    auth.requestMatchers(HttpMethod.PUT, "/pacientes").hasRole("MEDICO");
//                    auth.requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("MEDICO");
                    auth.requestMatchers(HttpMethod.GET, "/consultas").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/consultas").permitAll();
                    auth.requestMatchers(HttpMethod.PUT, "/consultas").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE, "/consultas").permitAll();
//                    auth.requestMatchers(HttpMethod.GET, "/consultas").hasRole("MEDICO");
//                    auth.requestMatchers(HttpMethod.POST, "/consultas").hasRole("MEDICO");
//                    auth.requestMatchers(HttpMethod.PUT, "/consultas").hasRole("MEDICO");
//                    auth.requestMatchers(HttpMethod.DELETE, "/consultas").hasRole("MEDICO");

                    auth.requestMatchers(HttpMethod.GET, "/consultas").hasRole("SECRETARIA");
                    auth.requestMatchers(HttpMethod.POST, "/consultas").hasRole("SECRETARIA");
                    auth.requestMatchers(HttpMethod.PUT, "/consultas").hasRole("SECRETARIA");
                    auth.requestMatchers(HttpMethod.DELETE, "/consultas").hasRole("SECRETARIA");

                    //inicialmente permitindo todas as consultas, para testes
                    auth.requestMatchers(HttpMethod.GET, "/disponibilidades").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/disponibilidades").permitAll();
                    auth.requestMatchers(HttpMethod.PUT, "/disponibilidades").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE, "/disponibilidades").permitAll();

//                    auth.requestMatchers(HttpMethod.GET, "/disponibilidades").hasRole("SECRETARIA");
//                    auth.requestMatchers(HttpMethod.POST, "/disponibilidades").hasRole("SECRETARIA");
//                    auth.requestMatchers(HttpMethod.PUT, "/disponibilidades").hasRole("SECRETARIA");
//                    auth.requestMatchers(HttpMethod.DELETE, "/disponibilidades").hasRole("SECRETARIA");

                    auth.requestMatchers(HttpMethod.GET, "/pacientes").hasRole("SECRETARIA");
                    auth.requestMatchers(HttpMethod.POST, "/pacientes").hasRole("SECRETARIA");
                    auth.requestMatchers(HttpMethod.PUT, "/pacientes").hasRole("SECRETARIA");
                    auth.requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("SECRETARIA");

                    auth.requestMatchers(HttpMethod.GET, "/endereco").hasRole("SECRETARIA");
                    auth.requestMatchers(HttpMethod.POST, "/endereco").hasRole("SECRETARIA");
                    auth.requestMatchers(HttpMethod.PUT, "/endereco").hasRole("SECRETARIA");
                    auth.requestMatchers(HttpMethod.DELETE, "/endereco").hasRole("SECRETARIA");

                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


}
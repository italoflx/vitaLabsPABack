package vitalabs.com.clinica.controller;

import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vitalabs.com.clinica.config.AuthenticatedDTO;
import vitalabs.com.clinica.config.LoginDTO;
import vitalabs.com.clinica.service.TokenService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public String token(@RequestBody @Valid LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));
        return tokenService.generateToken(authentication);
    }

    @PostMapping("/verificarToken")
    public Boolean verificarToken(@RequestBody @Valid AuthenticatedDTO authenticatedDTO) {
        return tokenService.isTokenValid(authenticatedDTO.token());
    }
}
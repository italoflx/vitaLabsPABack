package vitalabs.com.clinica.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vitalabs.com.clinica.model.Secretaria;
import vitalabs.com.clinica.service.SecretariaService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/secretarias")
public class SecretariaController {
    SecretariaService service;
    ModelMapper mapper;

    public SecretariaController(SecretariaService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Secretaria.DtoResponse> list(){
        return this.service.list().stream().map(
                elementoAtual -> {
                    Secretaria.DtoResponse response = Secretaria.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }
        ).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Secretaria.DtoResponse create(@RequestBody Secretaria.DtoRequest s){
        Secretaria secretaria = this.service.create(Secretaria.DtoRequest.convertToEntity(s, mapper));
        Secretaria.DtoResponse response = Secretaria.DtoResponse.convertToDto(secretaria, mapper);
        response.generateLinks(secretaria.getId());
        return response;
    }

    @GetMapping("{id}")
    public Secretaria.DtoResponse getById(@PathVariable String id){
        Secretaria secretaria = this.service.getById(id);

        Secretaria.DtoResponse response = Secretaria.DtoResponse.convertToDto(secretaria, mapper);

        response.generateLinks(secretaria.getId());
        return response;
    }

    @PutMapping("/{id}")
    public Secretaria.DtoResponse update(@RequestBody Secretaria.DtoRequest dtoRequest, @PathVariable String id) {
        Secretaria secretaria = Secretaria.DtoRequest.convertToEntity(dtoRequest, mapper);
        Secretaria.DtoResponse response = Secretaria.DtoResponse.convertToDto(this.service.update(secretaria, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        this.service.delete(id);
    }
}

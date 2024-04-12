package vitalabs.com.clinica.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vitalabs.com.clinica.model.Disponibilidade;
import vitalabs.com.clinica.service.DisponibilidadeService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/disponibilidades")
public class DisponibilidadeController {
    DisponibilidadeService service;
    ModelMapper mapper;

    public DisponibilidadeController(DisponibilidadeService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Disponibilidade.DtoResponse> list(){
        return this.service.list().stream().map(
                elementoAtual -> {
                    Disponibilidade.DtoResponse response = Disponibilidade.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Disponibilidade.DtoResponse create(@RequestBody Disponibilidade.DtoRequest p){
        Disponibilidade disponibilidade = this.service.create(Disponibilidade.DtoRequest.convertToEntity(p, mapper));

        Disponibilidade.DtoResponse response = Disponibilidade.DtoResponse.convertToDto(disponibilidade, mapper);
        response.generateLinks(disponibilidade.getId());

        return response;
    }

    @GetMapping("{id}")
    public Disponibilidade.DtoResponse getById(@PathVariable String id){
        Disponibilidade disponibilidade = this.service.getById(id);
        Disponibilidade.DtoResponse response = Disponibilidade.DtoResponse.convertToDto(disponibilidade, mapper);
        response.generateLinks(disponibilidade.getId());

        return response;
    }

    @PutMapping("/{id}")
    public Disponibilidade.DtoResponse update(@RequestBody Disponibilidade.DtoRequest dtoRequest, @PathVariable String id) {
        Disponibilidade u = Disponibilidade.DtoRequest.convertToEntity(dtoRequest, mapper);
        Disponibilidade.DtoResponse response = Disponibilidade.DtoResponse.convertToDto(this.service.update(u, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        this.service.delete(id);
    }
}

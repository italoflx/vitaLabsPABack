package vitalabs.com.clinica.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vitalabs.com.clinica.model.Medico;
import vitalabs.com.clinica.service.MedicoService;

import java.util.List;

@RestController
@RequestMapping("/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {
    MedicoService service;
    ModelMapper mapper;

    public MedicoController(MedicoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Medico.DtoResponse> list(){
        return this.service.list().stream().map(
                elementoAtual -> {
                    Medico.DtoResponse response = Medico.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }
        ).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medico.DtoResponse create(@RequestBody Medico.DtoRequest m){
        Medico medico = this.service.create(Medico.DtoRequest.convertToEntity(m, mapper));

        Medico.DtoResponse response = Medico.DtoResponse.convertToDto(medico, mapper);
        response.generateLinks(medico.getId());

        return response;
    }

    @GetMapping("{id}")
    public Medico.DtoResponse getById(@PathVariable String id){
        Medico medico = this.service.getById(id);

        Medico.DtoResponse response = Medico.DtoResponse.convertToDto(medico, mapper);

        response.generateLinks(medico.getId());
        return response;
    }

    @PutMapping("/{id}")
    public Medico.DtoResponse update(@RequestBody Medico.DtoRequest dtoRequest, @PathVariable String id) {
        Medico medico = Medico.DtoRequest.convertToEntity(dtoRequest, mapper);
        Medico.DtoResponse response = Medico.DtoResponse.convertToDto(this.service.update(medico, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        this.service.delete(id);
    }
}

package vitalabs.com.clinica.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vitalabs.com.clinica.model.Consulta;
import vitalabs.com.clinica.model.Disponibilidade;
import vitalabs.com.clinica.service.ConsultaService;
import java.util.List;

@RestController
@RequestMapping("/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {
    ConsultaService service;
    ModelMapper mapper;

    public ConsultaController(ConsultaService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Consulta.DtoResponse> list(){
        return this.service.list().stream().map(
            elemento -> {
                Consulta.DtoResponse response = Consulta.DtoResponse.convertToDto(elemento, this.mapper);
                response.generateLinks(elemento.getId());

                return response;
            }
        ).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consulta.DtoResponse create(@RequestBody Consulta.DtoRequest c){
        Consulta consulta = this.service.create(c);
        return Consulta.DtoResponse.convertToDto(consulta, this.mapper);
    }

    @PutMapping
    public Consulta.DtoResponse update(@RequestBody Consulta.DtoRequest c, @PathVariable String id) {
        Consulta consulta = this.service.update(Consulta.DtoRequest.convertToEntity(c, this.mapper), id);
        return Consulta.DtoResponse.convertToDto(consulta, this.mapper);
    }

    @GetMapping("{id}")
    public Consulta.DtoResponse getById(@PathVariable String id){
        Consulta consulta = this.service.findById(id);
        Consulta.DtoResponse response = Consulta.DtoResponse.convertToDto(consulta, mapper);
        response.generateLinks(consulta.getId());

        return response;
    }

    @DeleteMapping
    public void delete(@PathVariable String id){
        this.service.delete(id);
    }
}

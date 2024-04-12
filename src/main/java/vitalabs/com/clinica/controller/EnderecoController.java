package vitalabs.com.clinica.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vitalabs.com.clinica.model.Endereco;
import vitalabs.com.clinica.service.EnderecoService;

import java.util.List;

@RestController
@RequestMapping("/endereco")
@CrossOrigin(origins = "*")
public class EnderecoController {
    EnderecoService service;
    ModelMapper mapper;

    public EnderecoController(EnderecoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Endereco.DtoResponse> list(){
        return this.service.list().stream().map(
                elemento -> {
                    Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(elemento, this.mapper);
                    response.generateLinks(elemento.getId());

                    return response;
                }
        ).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco.DtoResponse create(@RequestBody Endereco.DtoRequest e){
        Endereco endereco = this.service.create(Endereco.DtoRequest.convertToEntity(e, mapper));
        Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(endereco, mapper);
        response.generateLinks(endereco.getId());
        return response;
    }

    @PutMapping
    public Endereco.DtoResponse update(@RequestBody Endereco.DtoRequest c, @PathVariable String id) {
        Endereco endereco = this.service.update(Endereco.DtoRequest.convertToEntity(c, this.mapper), id);
        return Endereco.DtoResponse.convertToDto(endereco, this.mapper);
    }

    @GetMapping("{id}")
    public Endereco.DtoResponse getById(@PathVariable String id){
        Endereco endereco = this.service.getById(id);
        Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(endereco, mapper);
        response.generateLinks(endereco.getId());

        return response;
    }

    @DeleteMapping
    public void delete(@PathVariable String id){
        this.service.delete(id);
    }
}

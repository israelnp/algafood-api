package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.jpa.CadastroCozinha;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaRepository.listar());
    }


    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){

        //HttpHeaders headers = new HttpHeaders();
        //headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
        //return ResponseEntity.ok().body(cozinhaRepository.buscar(cozinhaId));
        //return ResponseEntity.ok(cozinhaRepository.buscar(cozinhaId));


//        return ResponseEntity
//                    .status(HttpStatus.FOUND)
//                    .headers(headers)
//                    .build();

        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
        if(cozinha!=null){
            return ResponseEntity.ok(cozinha);
        }


        return ResponseEntity.notFound().build();

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinha.salvar(cozinha);
    }

//    @PostMapping
//    public ResponseEntity<Void> save(@RequestBody Cozinha cozinha){
//
//        Cozinha aux =cozinhaRepository.salvar(cozinha);
//        URI uri = getUri(aux);
//        return ResponseEntity.created(uri).build();
//    }
//
//    private URI getUri(Cozinha aux) {
//        return ServletUriComponentsBuilder
//                    .fromCurrentRequest()
//                    .path("/{cozinhaId}")
//                    .buildAndExpand(aux.getId())
//                    .toUri();
//    }
    @PutMapping("/{cozinhaId}")
    public ResponseEntity<?> update(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
    try {
        Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);
        if(cozinhaAtual != null){
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
            cozinhaAtual =  cadastroCozinha.salvar(cozinhaAtual);
            return ResponseEntity.ok(cozinhaAtual);
        }
        return ResponseEntity.notFound().build();
    } catch (EntidadeNaoEncontradaException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){

        try {
            cadastroCozinha.excluir(cozinhaId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

}

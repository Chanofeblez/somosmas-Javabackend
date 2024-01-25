package com.somosmas.coordinadores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoordinadorController {
    private CoordinatorService coordinadorService;

    @Autowired
    public CoordinadorController() {
    }

    public CoordinadorController(CoordinatorService coordinadorService) {
        this.coordinadorService = coordinadorService;
    }

    @GetMapping("/coordinadores")
    public List<Coordinador> getCoordinadores() {
        return coordinadorService.getCoordinadores();
    }

    @GetMapping("/coordinadores/{id}")
    public Coordinador getCoordinador(@PathVariable("id") Long coordinadorId) {
        return coordinadorService.getCoordinador(coordinadorId);
    }

//    @PostMapping("/coordinadores")
//    public ResponseEntity<Long> createCoordinador(@RequestBody Coordinador c){
//       // Long idCoordinador = coordinadorService.createCoordinador(c);
//        return new ResponseEntity<>(idCoordinador, HttpStatus.CREATED);
//    }

    @DeleteMapping("/coordinadores/{id}")
    public ResponseEntity<?> deleteCoordinador(@PathVariable("id") Long coordinadorId){
        coordinadorService.deleteCoordinador(coordinadorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("/coordinadores/{id}")
//    public Coordinador updateCoordinador(@PathVariable("id") Long coordinadorId, @RequestBody  Coordinador c){
//        return coordinadorService.updateCoordinador(coordinadorId, c);
//    }
}

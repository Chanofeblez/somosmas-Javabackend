package com.somosmas.eventos;

import com.somosmas.miembros.Miembro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventoController {

    private EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<Evento>> getEventos() {
        List<Evento> eventos = eventoService.getEventos();
        if( eventos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/eventos/{id}")
    public ResponseEntity<Evento> getEvento(@PathVariable("id") Long eventoId) {
        Evento evento = eventoService.getEvento(eventoId);
        if (evento==null){
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(evento);
    }

    @PostMapping("/eventos/create")
    public ResponseEntity<Evento> createEvento(@RequestBody Evento e){
        Evento evento = eventoService.createEvento(e);
        return new ResponseEntity<>(evento, HttpStatus.CREATED);
    }

    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<?> deleteEvento(@PathVariable("id") Long eventoId){
        eventoService.deleteEvento(eventoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/eventos/{eventoId}")
    public Evento updateEvento(@PathVariable("eventoId") Long eventoId, @RequestBody Evento e){
        return eventoService.updateEvento(eventoId, e);
    }
}

package com.somosmas.eventos;

import com.somosmas.miembros.Miembro;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class EventoService {

    private EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Transactional(readOnly = true)
    public List<Evento> getEventos() {
        return eventoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Evento getEvento(Long eventoId){
        //Check si existe evento con ese id, si no, botamos un Error
        Evento eventoExistente = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NoSuchElementException("Evento con ese id no existe, id: " + eventoId));

        return eventoExistente;
    }

    public Evento createEvento(Evento e){
        Evento eventoGuardado = eventoRepository.save(e);
        return eventoGuardado;
    }

    public void deleteEvento(Long eventoId){
        //Check si id existe, si no, imprimimos Warning
        boolean eventoExiste = eventoRepository.existsById(eventoId);

        if (!eventoExiste){
            throw new NoSuchElementException("Evento con id " + eventoId + "no existe");
        }
        eventoRepository.deleteById(eventoId);
    }

    public Evento updateEvento(Long eventoId, Evento eventoAActualizar) {

        //Check si existe miembro con ese id, si no, botamos un Error
        Evento eventoExistente = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NoSuchElementException("Evento con ese id no existe, id: " + eventoId));

        //Actualizar miembro
        eventoExistente.setNombre(eventoAActualizar.getNombre());
        eventoExistente.setDescripcion(eventoAActualizar.getDescripcion());
        eventoExistente.setFecha(eventoAActualizar.getFecha());
        eventoExistente.setImagenUrl(eventoAActualizar.getImagenUrl());

        return eventoExistente;
    }
}

package com.somosmas.coordinadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Transactional
@Service
public class CoordinatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinatorService.class);
    private CoordinatorRepository coordinadorRepository;

    @Autowired
    public CoordinatorService() {
    }

    public CoordinatorService(CoordinatorRepository coordinadorRepository) {
        this.coordinadorRepository = coordinadorRepository;
    }

    @Transactional(readOnly = true)
    public List<Coordinador> getCoordinadores(){
        return coordinadorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Coordinador getCoordinador(Long coordinadorId){
        //Check si existe miembro con ese id, si no, botamos un Error
        Coordinador coordinadorExistente = coordinadorRepository.findById(coordinadorId)
                .orElseThrow(() -> new NoSuchElementException("Miembro con ese id no existe, id: " + coordinadorId));

        return coordinadorExistente;
    }

//    public Long createCoordinador(Coordinador c){
//        LOGGER.info("Creando coordinador {}", c);
//        //Check si el email es valido
//        if(!checkValidezEmail(c.getEmail())){
//            LOGGER.warn("Email {} no es valido", c.getEmail());
//            throw new IllegalArgumentException("Email " + c.getEmail() + "no es valido");
//        }
//
//        //Check si el email ya existe
//        boolean emailExiste = coordinadorRepository.existsByEmail(c.getEmail());
//        if (emailExiste){
//            LOGGER.warn("Email {} ya esta registrado", c.getEmail());
//            throw new IllegalArgumentException("Email " + c.getEmail() + "ya esta registrado.");
//        }
//
//        Coordinador coordinadorGuardado = coordinadorRepository.save(c);
//        LOGGER.info("Miembro con id {} fue guardado exitosamente", coordinadorGuardado.getId());
//        return coordinadorGuardado.getId();
//    }

    public void deleteCoordinador(Long coordinadorId){
        //Check si id existe, si no, imprimimos Warning
        boolean coordinadorExiste = coordinadorRepository.existsById(coordinadorId);

        if (!coordinadorExiste){
            throw new NoSuchElementException("Coordinador con id " + coordinadorId + "no existe");
        }
        coordinadorRepository.deleteById(coordinadorId);
    }


//    public Coordinador updateCoordinador(Long coordinadorId, Coordinador coordinadorAActualizar){
//        //Check si existe miembro con ese id, si no, botamos un Error
//        Coordinador coordinadorExistente = coordinadorRepository.findById(coordinadorId)
//                .orElseThrow(() -> new NoSuchElementException("Miembro con ese id no existe, id: " + coordinadorId));
//
//        //Actualizar miembro
//        coordinadorExistente.setNombre(coordinadorAActualizar.getNombre());
//        coordinadorExistente.setPrimerApellido(coordinadorAActualizar.getPrimerApellido());
//        coordinadorExistente.setSegundoApellido(coordinadorAActualizar.getSegundoApellido());
//        coordinadorExistente.setEmail(coordinadorAActualizar.getEmail());
//        coordinadorExistente.setTelefono(coordinadorAActualizar.getTelefono());
//        coordinadorExistente.setCiudad(coordinadorAActualizar.getCiudad());
//        coordinadorExistente.setPais(coordinadorAActualizar.getPais());
//
//        //Check si el email es valido
//        if(!checkValidezEmail(coordinadorAActualizar.getEmail())){
//            throw new IllegalArgumentException("Email " + coordinadorAActualizar.getEmail() + " no es valido");
//        }
//
//        //Check si el email que se quiere actualizar ya existe
//        if (!coordinadorAActualizar.getEmail().equals(coordinadorExistente.getEmail())){
//            boolean emailExiste = coordinadorRepository.existsByEmail(coordinadorAActualizar.getEmail());
//            if (emailExiste){
//                throw new IllegalArgumentException("Email " + coordinadorAActualizar.getEmail() + "ya esta registrado.");
//            }
//        }
//
//        return coordinadorExistente;
//    }

    private boolean checkValidezEmail( String email){
        return Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9,-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE
        ).asPredicate().test(email);
    }
}

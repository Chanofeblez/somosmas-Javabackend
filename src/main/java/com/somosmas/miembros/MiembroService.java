package com.somosmas.miembros;

import com.somosmas.rol.Rol;
import com.somosmas.rol.RolRepository;
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
public class MiembroService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiembroService.class);
    private MiembroRepository miembroRepository;
    private final RolRepository rolRepository;

    @Autowired
    public MiembroService(MiembroRepository miembroRepository,
                          RolRepository rolRepository) {
        this.miembroRepository = miembroRepository;
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public List<Miembro> getMiembros(){
        return miembroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Miembro getMiembro(Long miembroId){
        //Check si existe miembro con ese id, si no, botamos un Error
        Miembro miembroExistente = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new NoSuchElementException("Miembro con ese id no existe, id: " + miembroId));

        return miembroExistente;
    }

    public Miembro createMiembro(Miembro m){
        LOGGER.info("Creando miembro {}", m);
        //Check si el email es valido
        if(!checkValidezEmail(m.getEmail())){
            LOGGER.warn("Email {} no es valido", m.getEmail());
            throw new IllegalArgumentException("Email " + m.getEmail() + "no es valido");
        }

        //Check si el email ya existe
        boolean emailExiste = miembroRepository.existsByEmail(m.getEmail());
        if (emailExiste){
            LOGGER.warn("Email {} ya esta registrado", m.getEmail());
            throw new IllegalArgumentException("The email " + m.getEmail() + " already exists.");
        }

        m.setUnRol("miembro");

        Miembro miembroGuardado = miembroRepository.save(m);
        LOGGER.info("Miembro con id {} fue guardado exitosamente", miembroGuardado.getId());
        return miembroGuardado;
    }

    public Miembro loginMiembro(Miembro m){

        Miembro miembroLogin;

        //Check si el email existe
        boolean emailExiste = miembroRepository.existsByEmail(m.getEmail());
        if (!emailExiste){
            throw new IllegalArgumentException("The email/password is incorrect." );
        }
        miembroLogin = miembroRepository.findByEmail(m.getEmail());

        //Check si el password esta correcto
        if(!miembroLogin.getPassword().equals(m.getPassword())){
            throw new IllegalArgumentException("The email/password is  incorrect.");
        }


        return miembroLogin;
    }

    public void deleteMiembro(Long miembroId){
        //Check si id existe, si no, imprimimos Warning
        boolean miembroExiste = miembroRepository.existsById(miembroId);

        if (!miembroExiste){
            throw new NoSuchElementException("Miembro con id " + miembroId + "no existe");
        }
        miembroRepository.deleteById(miembroId);
    }


    public Miembro updateMiembro(Long miembroId, Miembro miembroAActualizar){
        //Check si existe miembro con ese id, si no, botamos un Error
        Miembro miembroExistente = miembroRepository.findById(miembroId)
                        .orElseThrow(() -> new NoSuchElementException("Miembro con ese id no existe, id: " + miembroId));

        //Actualizar miembro
        miembroExistente.setNombre(miembroAActualizar.getNombre());
        miembroExistente.setPrimerApellido(miembroAActualizar.getPrimerApellido());
        miembroExistente.setSegundoApellido(miembroAActualizar.getSegundoApellido());
        miembroExistente.setEmail(miembroAActualizar.getEmail());
        miembroExistente.setTelefono(miembroAActualizar.getTelefono());
        miembroExistente.setCiudad(miembroAActualizar.getCiudad());
        miembroExistente.setPais(miembroAActualizar.getPais());

        //Check si el email es valido
        if(!checkValidezEmail(miembroAActualizar.getEmail())){
            throw new IllegalArgumentException("Email " + miembroAActualizar.getEmail() + " no es valido");
        }

        //Check si el email que se quiere actualizar ya existe
        if (!miembroAActualizar.getEmail().equals(miembroExistente.getEmail())){
           boolean emailExiste = miembroRepository.existsByEmail(miembroAActualizar.getEmail());
           if (emailExiste){
               throw new IllegalArgumentException("Email " + miembroAActualizar.getEmail() + "ya esta registrado.");
           }
        }

        return miembroExistente;
    }

    private boolean checkValidezEmail( String email){
        return Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9,-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE
        ).asPredicate().test(email);
    }


    public Miembro editarRolMiembro(Long miembroId, Miembro miembroAActualizar) {
        //Check si existe miembro con ese id, si no, botamos un Error
        Miembro miembroExistente = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new NoSuchElementException("Miembro con ese id no existe, id: " + miembroId));

        //Actualizar miembro
        miembroExistente.setNombre(miembroAActualizar.getNombre());
        miembroExistente.setPrimerApellido(miembroAActualizar.getPrimerApellido());
        miembroExistente.setSegundoApellido(miembroAActualizar.getSegundoApellido());
        miembroExistente.setEmail(miembroAActualizar.getEmail());
        miembroExistente.setTelefono(miembroAActualizar.getTelefono());
        miembroExistente.setCiudad(miembroAActualizar.getCiudad());
        miembroExistente.setPais(miembroAActualizar.getPais());
        miembroExistente.setUnRol(miembroAActualizar.getUnRol());

        return miembroExistente;
    }
}


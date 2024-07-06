package com.somosmas.miembros;

import com.somosmas.role.IRoleRepository;
import com.somosmas.role.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Transactional
@Service
public class MiembroService implements IMiembroService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MiembroService.class);
    private IMiembroRepository miembroRepository;
    private final IRoleRepository rolRepository;

    @Autowired
    public MiembroService(IMiembroRepository miembroRepository,
                          IRoleRepository rolRepository) {
        this.miembroRepository = miembroRepository;
        this.rolRepository = rolRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Miembro> getMiembros(){
        return miembroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Miembro> findMiembroById(Long miembroId){
        //Check si existe miembro con ese id, si no, botamos un Error
        Miembro miembroExistente = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new NoSuchElementException("Miembro con ese id no existe, id: " + miembroId));

        return Optional.ofNullable(miembroExistente);
    }

    @Override
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

    @Override
    public void deleteMiembro(Long miembroId){
        //Check si id existe, si no, imprimimos Warning
        boolean miembroExiste = miembroRepository.existsById(miembroId);

        if (!miembroExiste){
            throw new NoSuchElementException("Miembro con id " + miembroId + "no existe");
        }
        miembroRepository.deleteById(miembroId);
    }

    @Override
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


   /* public Miembro editarRolMiembro(Long miembroId, Miembro miembroAActualizar) {
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
    } */



    @Override
    public String encriptPassword(String password) {

        return new BCryptPasswordEncoder().encode(password);
    }
}


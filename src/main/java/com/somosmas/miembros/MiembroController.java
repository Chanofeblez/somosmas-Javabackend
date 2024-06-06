package com.somosmas.miembros;

import com.somosmas.rol.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MiembroController {

    private MiembroService miembroService;

    @Autowired
    public MiembroController(MiembroService miembroService) {
        this.miembroService = miembroService;
    }

    @GetMapping("/miembros")
    public List<Miembro> getMiembros() {
        return miembroService.getMiembros();
    }

    @GetMapping("/miembros/miembro")
    public Miembro getMiembro(@RequestBody Miembro m)
            //(@PathVariable("id") Long miembroId)
     {
        return miembroService.getMiembro(m.getId());
    }

    @PostMapping("/miembros/register")
    public ResponseEntity<Miembro> createMiembro(@RequestBody Miembro m){
        Miembro miembro = miembroService.createMiembro(m);
        return new ResponseEntity<>(miembro, HttpStatus.CREATED);
    }

    @PostMapping("/miembros/login")
    public ResponseEntity<Miembro> loginMiembro(@RequestBody Miembro m){
        Miembro miembro = miembroService.loginMiembro(m);
        return new ResponseEntity<>(miembro, HttpStatus.CREATED);
    }

    @DeleteMapping("/miembros/{id}")
    public ResponseEntity<?> deleteMiembro(@PathVariable("id") Long miembroId){
        miembroService.deleteMiembro(miembroId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/miembros/{miembroId}")
    public Miembro updateMiembro(@PathVariable("miembroId") Long miembroId, @RequestBody Miembro m){
      return miembroService.updateMiembro(miembroId, m);
    }

    @PutMapping("/actualizarrol/{miembroId}")
    public Miembro editarRolMiembro(@PathVariable("miembroId") Long miembroId, @RequestBody Miembro m){
        return miembroService.editarRolMiembro(miembroId, m);
    }
}

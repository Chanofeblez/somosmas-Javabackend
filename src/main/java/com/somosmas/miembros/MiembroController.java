package com.somosmas.miembros;

import com.somosmas.role.IRoleService;
import com.somosmas.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/members")
@PreAuthorize("denyAll()")
public class MiembroController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMiembroService miembroService;

    @Autowired
    public MiembroController(MiembroService miembroService) {
        this.miembroService = miembroService;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<Miembro> getMiembros() {
        return miembroService.getMiembros();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Miembro> getMiembro(@PathVariable Long miembroId) {
        Optional<Miembro> user = miembroService.findMiembroById(miembroId);
        return user.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Miembro> createMiembro(@RequestBody Miembro miembro){
        Set<Role> roleList = new HashSet<Role>();
        Role readRole;

        //Encriptamos Contraseña
        miembro.setPassword(miembroService.encriptPassword(miembro.getPassword()));

        //Recuperar la Permission/s por su ID
        for(Role role : miembro.getRolesList()){
            readRole = roleService.findById(role.getId()).orElse(null);
            if (readRole != null ){
                //si encuentro, guardo en la lista
                roleList.add(readRole);
            }
        }

        if (!roleList.isEmpty()) {
            miembro.setRolesList(roleList);

            Miembro newUser = miembroService.createMiembro(miembro);
            return ResponseEntity.ok(newUser);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMiembro(@PathVariable("id") Long miembroId){
        miembroService.deleteMiembro(miembroId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Miembro updateMiembro(@PathVariable("miembroId") Long miembroId, @RequestBody Miembro m){
      return miembroService.updateMiembro(miembroId, m);
    }
}

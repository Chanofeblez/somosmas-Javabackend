package com.somosmas.miembros;

import com.somosmas.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "miembros")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Miembro {

    @Id
    @Column(name="id_miembro")
    @SequenceGenerator(
            sequenceName = "sequence_miembro",
            name = "sequence_miembro"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "sequence_miembro"
    )
    private Long id;
    @Column(name = "nombre", nullable = false)//, nullable = false
    private String nombre;
    @Column(name = "primer_apellido", nullable = false)//, nullable = false
    private String primerApellido;
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Column(name = "telefono", nullable = false)//, nullable = false
    private String telefono;
    @Column(name = "email", nullable = false, unique = true)//, nullable = false, unique = true
    private String email;
    @Column(name = "password")//, nullable = true
    private String password;
    @Column(name = "ciudad", nullable = false)//, nullable = false
    private String ciudad;
    @Column(name = "pais", nullable = false)//, nullable = false
    private String pais;
    private boolean enabled;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialNotExpired;

    //Usamos Set porque no permite repetidos
    //List permite repetidos
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //el eager me va a cargar todos los roles
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> rolesList = new HashSet<>();


}

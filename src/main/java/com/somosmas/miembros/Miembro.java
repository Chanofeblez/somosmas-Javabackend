package com.somosmas.miembros;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.somosmas.rol.Rol;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "miembro")
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

// Esto es para cuando cree las tablas Roll
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(
//            name="fk_rol",
//            referencedColumnName = "id_rol"
//    )
    private String unRol;

    public Miembro() {
    }

    public Miembro(String nombre, String primerApellido, String segundoApellido, String telefono, String email, String password, String ciudad, String pais) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.ciudad = ciudad;
        this.pais = pais;
        this.unRol = "miembro";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnRol() {
        return unRol;
    }

    public void setUnRol(String unRol) {
        this.unRol = unRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Miembro miembro)) return false;
        return Objects.equals(getId(), miembro.getId()) && Objects.equals(getNombre(), miembro.getNombre()) && Objects.equals(getPrimerApellido(), miembro.getPrimerApellido()) && Objects.equals(getSegundoApellido(), miembro.getSegundoApellido()) && Objects.equals(getTelefono(), miembro.getTelefono()) && Objects.equals(getEmail(), miembro.getEmail()) && Objects.equals(getPassword(), miembro.getPassword()) && Objects.equals(getCiudad(), miembro.getCiudad()) && Objects.equals(getPais(), miembro.getPais()) && Objects.equals(getUnRol(), miembro.getUnRol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getPrimerApellido(), getSegundoApellido(), getTelefono(), getEmail(), getPassword(), getCiudad(), getPais(), getUnRol());
    }

    @Override
    public String toString() {
        return "Miembro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", pais='" + pais + '\'' +
                ", unRol=" + unRol +
                '}';
    }
}

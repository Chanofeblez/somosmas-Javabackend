package com.somosmas.miembros;

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
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_miembro"
    )
    private Long id;
    @Column(name = "nombre")//, nullable = false
    private String nombre;
    @Column(name = "primer_apellido")//, nullable = false
    private String primerApellido;
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Column(name = "telefono")//, nullable = false
    private String telefono;
    @Column(name = "email")//, nullable = false, unique = true
    private String email;
    @Column(name = "password")//, nullable = true
    private String password;
    @Column(name = "ciudad")//, nullable = false
    private String ciudad;
    @Column(name = "pais")//, nullable = false
    private String pais;

    public Miembro() {
    }

    public Miembro(Long id, String nombre, String primerApellido, String segundoApellido, String telefono, String email,String password, String ciudad, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.ciudad = ciudad;
        this.pais = pais;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Miembro miembro = (Miembro) o;
        return Objects.equals(id, miembro.id) && Objects.equals(nombre, miembro.nombre) && Objects.equals(primerApellido, miembro.primerApellido) && Objects.equals(segundoApellido, miembro.segundoApellido) && Objects.equals(telefono, miembro.telefono) && Objects.equals(email, miembro.email) && Objects.equals(password, miembro.password) && Objects.equals(ciudad, miembro.ciudad) && Objects.equals(pais, miembro.pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, primerApellido, segundoApellido, telefono, email, password, ciudad, pais);
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
                '}';
    }
}

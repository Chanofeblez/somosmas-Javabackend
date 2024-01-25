package com.somosmas.coordinadores;

import com.somosmas.miembros.Miembro;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "coordinador")
public class Coordinador  {

    @Id
    @Column(name="id_coordinador")
    @SequenceGenerator(
            sequenceName = "sequence_coordinador",
            name = "sequence_coordinador"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_coordinador"
    )
    private Long id;
    @Column(name = "ciudadCoordinacion")
    String ciudadCoordinacion="";

    public Coordinador() {
    }

    public Coordinador(String ciudadCoordinacion) {
        this.ciudadCoordinacion = ciudadCoordinacion;
    }

    public Coordinador(Long id,String nombre, String primerApellido, String segundoApellido, String telefono, String email, String ciudad, String pais, String ciudadCoordinacion) {
        //super(id,nombre, primerApellido, segundoApellido, telefono, email, ciudad, pais);
        this.ciudadCoordinacion = ciudadCoordinacion;
    }

    public String getCiudadCoordinacion() {
        return ciudadCoordinacion;
    }

    public void setCiudadCoordinacion(String ciudadCoordinacion) {
        this.ciudadCoordinacion = ciudadCoordinacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Coordinador that = (Coordinador) o;
        return Objects.equals(ciudadCoordinacion, that.ciudadCoordinacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ciudadCoordinacion);
    }

    @Override
    public String toString() {
        return "Coordinador{" +
                "ciudadCoordinacion='" + ciudadCoordinacion + '\'' +
                '}';
    }
}

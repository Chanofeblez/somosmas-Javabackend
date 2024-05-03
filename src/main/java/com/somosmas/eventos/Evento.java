package com.somosmas.eventos;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @Column(name="id_evento")
    @SequenceGenerator(
            sequenceName = "sequence_evento",
            name = "sequence_evento"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "sequence_evento"
    )
    private Long id;
    @Column(name = "nombre")//, nullable = false
    private String nombre;
    @Column(name = "descripcion")//, nullable = false
    private String descripcion;
    @Column(name = "imagenUrl")
    private String imagenUrl;
    @Column(name = "fecha")//, nullable = false
    private String fecha;

    public Evento() {
    }

    public Evento(Long id, String nombre, String descripcion, String imagenUrl, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.fecha = fecha;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento evento)) return false;
        return Objects.equals(getId(), evento.getId()) && Objects.equals(getNombre(), evento.getNombre()) && Objects.equals(getDescripcion(), evento.getDescripcion()) && Objects.equals(getImagenUrl(), evento.getImagenUrl()) && Objects.equals(getFecha(), evento.getFecha());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getDescripcion(), getImagenUrl(), getFecha());
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}

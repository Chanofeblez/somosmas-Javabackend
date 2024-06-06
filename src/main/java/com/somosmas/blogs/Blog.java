package com.somosmas.blogs;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "blog")
public class Blog {

    @Id
    @Column(name="id_blog")
    @SequenceGenerator(
            sequenceName = "sequence_blog",
            name = "sequence_blog"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "sequence_blog"
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

    public Blog() {
    }

    public Blog(String nombre, String descripcion, String imagenUrl, String fecha) {
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
        if (!(o instanceof Blog blog)) return false;
        return Objects.equals(getId(), blog.getId()) && Objects.equals(getNombre(), blog.getNombre()) && Objects.equals(getDescripcion(), blog.getDescripcion()) && Objects.equals(getImagenUrl(), blog.getImagenUrl()) && Objects.equals(getFecha(), blog.getFecha());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getDescripcion(), getImagenUrl(), getFecha());
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}

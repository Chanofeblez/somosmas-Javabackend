package com.somosmas.rol;

import com.somosmas.miembros.Miembro;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @Column(name="id_rol")
    @SequenceGenerator(
            sequenceName = "sequence_rol",
            name = "sequence_rol"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "sequence_rol"
    )
    private int id;
    private String nombreRol;
    private String descripcion;

    @OneToMany(mappedBy = "unRol")
    private List<Miembro> listaMiembro;

    public Rol() {
    }

    public Rol(String nombreRol, String descripcion, List<Miembro> listaMiembro) {
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.listaMiembro = listaMiembro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Miembro> getListaMiembro() {
        return listaMiembro;
    }

    public void setListaMiembro(List<Miembro> listaMiembro) {
        this.listaMiembro = listaMiembro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rol rol)) return false;
        return getId() == rol.getId() && Objects.equals(getNombreRol(), rol.getNombreRol()) && Objects.equals(getDescripcion(), rol.getDescripcion()) && Objects.equals(getListaMiembro(), rol.getListaMiembro());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombreRol(), getDescripcion(), getListaMiembro());
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", nombreRol='" + nombreRol + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", listaMiembro=" + listaMiembro +
                '}';
    }
}

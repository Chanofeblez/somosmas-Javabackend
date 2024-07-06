package com.somosmas.miembros;

import java.util.List;
import java.util.Optional;

public interface IMiembroService {

    public List<Miembro> getMiembros();
    public Optional<Miembro> findMiembroById(Long id);
    public Miembro createMiembro(Miembro miembro);
    public void deleteMiembro(Long id);
    public Miembro updateMiembro(Long id, Miembro miembro);
    public String encriptPassword(String password);
}

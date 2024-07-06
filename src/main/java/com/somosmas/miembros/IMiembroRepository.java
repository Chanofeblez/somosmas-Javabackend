package com.somosmas.miembros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMiembroRepository extends JpaRepository<Miembro, Long> {

boolean existsByEmail(String email);
Miembro findByEmail(String email);
Optional<Miembro> findUserEntityByEmail(String email);


}

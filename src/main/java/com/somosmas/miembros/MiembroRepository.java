package com.somosmas.miembros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {

boolean existsByEmail(String email);
Miembro findByEmail(String email);



}

package com.somosmas.coordinadores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatorRepository extends JpaRepository<Coordinador,Long>{

   // boolean existsByEmail(String email);
}

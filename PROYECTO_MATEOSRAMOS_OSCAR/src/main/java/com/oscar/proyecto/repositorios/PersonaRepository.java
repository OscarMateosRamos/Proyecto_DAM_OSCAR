/**
*Clase PersonaRepository.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscar.proyecto.modelo.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	
	Optional<Persona> findByUsuario(String usuario);


}

/**
*Clase EstudianteRepository.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oscar.proyecto.modelo.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
	@Query("SELECT e FROM Estudiante e LEFT JOIN FETCH e.grupo g WHERE LOWER(e.usuario) = LOWER(:usuario)")
	Estudiante buscarEstudianteCompleto(@Param("usuario") String usuario);
	
	
	
	@Query("""
		    SELECT e FROM Estudiante e
		    LEFT JOIN FETCH e.grupo g
		    WHERE e.idPersona = :idPersona
		""")
		Estudiante buscarPorPersonaId(Long idPersona);
}

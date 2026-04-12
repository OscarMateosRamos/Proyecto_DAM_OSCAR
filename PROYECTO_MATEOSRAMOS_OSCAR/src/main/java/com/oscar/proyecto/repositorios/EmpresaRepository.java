/**
*Clase EmpresaRepository.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oscar.proyecto.modelo.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	@Query("SELECT e FROM Empresa e LEFT JOIN FETCH e.tutores")
	List<Empresa> findAllConTutores();
}

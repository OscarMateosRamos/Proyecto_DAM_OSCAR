/**
*Clase ServicioProfesor.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Profesor;
import com.oscar.proyecto.repositorios.ProfesorRepository;

@Service
public class ServicioProfesor {

	@Autowired
	private ProfesorRepository profesorRepositorio;

	public List<Profesor> listarProfesores() {
		return profesorRepositorio.findAll();
	}

	public Profesor guardarProfesor(Profesor profesor) {
		return profesorRepositorio.save(profesor);
	}

	public void eliminarProfesor(Long idPersona) {

	    Profesor profesor = profesorRepositorio.findById(idPersona)
	            .orElseThrow(() -> new IllegalStateException("Profesor no encontrado"));

	    if (!profesor.getFormacionesCoordinadas().isEmpty()) {
	        throw new IllegalStateException(
	            "No se puede eliminar este profesor porque coordina formaciones en empresa borra la formacion primero."
	        );
	    }

	    profesorRepositorio.deleteById(idPersona);
	}


	public Profesor buscarPorId(Long id) {
		return profesorRepositorio.findById(id).orElse(null);
	}

}

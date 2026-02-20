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

	public void eliminarProfesor(Long id) {
		profesorRepositorio.deleteById(id);
	}

	public Profesor buscarPorId(Long id) {
		return profesorRepositorio.findById(id).orElse(null);
	}

}

/**
*Clase ServicioEstudiante.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Estudiante;
import com.oscar.proyecto.repositorios.EstudianteRepository;

@Service
public class ServicioEstudiante {

	@Autowired
	private EstudianteRepository estudianteRepositorio;

	public Estudiante guardarEstudiante(Estudiante e) {
		return estudianteRepositorio.save(e);
	}

	public List<Estudiante> listarEstudiantes() {
		return estudianteRepositorio.findAll();
	}

	public void eliminarEstudiante(Long idPersona) {

		Estudiante e = estudianteRepositorio.findById(idPersona)
				.orElseThrow(() -> new IllegalStateException("Estudiante no encontrado"));

		if (!e.getFormaciones().isEmpty()) {
			throw new IllegalStateException(
					"No se puede eliminar este profesor porque participa en una formacion borra su formacion primero.");
		}

		estudianteRepositorio.deleteById(idPersona);
	}

	public Estudiante obtenerEstudianteCompleto(String usuario) {
		return estudianteRepositorio.buscarEstudianteCompleto(usuario.toLowerCase());
	}

	public Estudiante obtenerEstudiantePorPersonaId(Long idPersona) {
		return estudianteRepositorio.buscarPorPersonaId(idPersona);
	}

}

/**
*Clase ServicioCurso.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Curso;
import com.oscar.proyecto.repositorios.CursoRepository;

@Service
public class ServicioCurso {

	@Autowired
	private CursoRepository cursoRepo;

	public List<Curso> listarCursos() {
		return cursoRepo.findAll();
	}

	public Curso guardarCurso(Curso curso) {
		return cursoRepo.save(curso);
	}

	public void eliminarCurso(Long id) {
		cursoRepo.deleteById(id);
	}

	public Curso buscarPorId(Long id) {
		return cursoRepo.findById(id).orElse(null);
	}
}

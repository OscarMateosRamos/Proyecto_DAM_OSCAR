/**
*Clase ServicioTutorEmpresa.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.TutorEmpresa;
import com.oscar.proyecto.repositorios.TutorEmpresaRepository;

@Service
public class ServicioTutorEmpresa {

	@Autowired
	private TutorEmpresaRepository tutorRepo;

	public List<TutorEmpresa> listarTutores() {
		return tutorRepo.findAll();
	}

	public Optional<TutorEmpresa> buscarPorId(Long id) {
		return tutorRepo.findById(id);
	}

	public TutorEmpresa guardarTutor(TutorEmpresa tutor) {
		return tutorRepo.save(tutor);
	}

	public void eliminarTutor(Long id) {
		tutorRepo.deleteById(id);
	}
}

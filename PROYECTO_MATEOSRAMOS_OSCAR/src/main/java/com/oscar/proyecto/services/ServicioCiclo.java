/**
* Clase ServicioCiclo.java
*
* @author Oscar Mateos Ramos
* @version
*/
package com.oscar.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Ciclo;
import com.oscar.proyecto.repositorios.CicloRepository;

@Service
public class ServicioCiclo {

	@Autowired
	private CicloRepository cicloRepo;

	public List<Ciclo> listarCiclos() {
		return cicloRepo.findAll();
	}

	public Ciclo guardarCiclo(Ciclo ciclo) {
		return cicloRepo.save(ciclo);
	}

	public void eliminarCiclo(Long id) {
		cicloRepo.deleteById(id);
	}

	public Ciclo buscarPorId(Long id) {
		return cicloRepo.findById(id).orElse(null);
	}
}

/**
*Clase ServicioGrupo.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Grupo;
import com.oscar.proyecto.repositorios.GrupoRepository;

@Service
public class ServicioGrupo {

	@Autowired
	private GrupoRepository grupoRepositorio;

	public List<Grupo> listarGrupos() {
		return grupoRepositorio.findAll();
	}

	public Grupo guardarGrupo(Grupo g) {
		return grupoRepositorio.save(g);
	}

	public void eliminarGrupo(Long idGrupo) {

		Grupo g = grupoRepositorio.findById(idGrupo).orElseThrow(
				() -> new IllegalStateException("Grupo no encontrado"));

		grupoRepositorio.deleteById(idGrupo);
	}

}

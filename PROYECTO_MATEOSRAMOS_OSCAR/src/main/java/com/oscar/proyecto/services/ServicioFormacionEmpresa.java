/**
*Clase ServicioFormacionEmpresa.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Estudiante;
import com.oscar.proyecto.modelo.FormacionEmpresa;
import com.oscar.proyecto.repositorios.FormacionEmpresaRepository;

@Service
public class ServicioFormacionEmpresa {

	@Autowired
	private FormacionEmpresaRepository formaEmpresaRepositorio;

	public List<FormacionEmpresa> listarFormaciones() {

		return formaEmpresaRepositorio.findAll();
	}

	public FormacionEmpresa guardar(FormacionEmpresa f) {

		return formaEmpresaRepositorio.save(f);
	}

	public void eliminar(Long idFormacion) {

		FormacionEmpresa f = formaEmpresaRepositorio.findById(idFormacion)
				.orElseThrow(() -> new IllegalStateException("Estudiante no encontrado"));

		formaEmpresaRepositorio.deleteById(idFormacion);

	}

}

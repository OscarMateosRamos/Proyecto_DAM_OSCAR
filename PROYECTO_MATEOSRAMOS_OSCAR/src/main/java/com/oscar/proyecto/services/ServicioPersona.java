/**
*Clase ServicioPersona.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.repositorios.PersonaRepository;

@Service
public class ServicioPersona {
	

	@Autowired
	private PersonaRepository personaRepositorio;

	public List<Persona> listarPersonas() {
		return  personaRepositorio.findAll();
	}

}

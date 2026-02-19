package com.oscar.proyecto.services;

import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Perfil;

@Service
public class ServicioSesion {

	private String nombrePersonaActual;
	private Perfil perfilActual;

	public String getNombrePersonaActual() {
		return nombrePersonaActual;
	}

	public void setNombrePersonaActual(String nombrePersonaActual) {
		this.nombrePersonaActual = nombrePersonaActual;
	}

	public Perfil getPerfil() {
		return perfilActual;
	}

	public void setPerfil(Perfil perfilActual) {
		this.perfilActual = perfilActual;
	}

	public void limpiarSesion() {
		this.nombrePersonaActual = null;
		this.perfilActual = null;
	}

	public boolean isAdmin() {
		return perfilActual == Perfil.ADMIN;
	}

	public boolean isProfesor() {
		return perfilActual == Perfil.PROFESOR;
	}

	public boolean isTutor() {
		return perfilActual == Perfil.TUTOR;
	}

	public boolean isEstudiante() {
		return perfilActual == Perfil.ESTUDIANTE;
	}
}

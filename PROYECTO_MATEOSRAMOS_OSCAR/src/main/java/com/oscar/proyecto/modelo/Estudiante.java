/**
*Clase Estudiante.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.modelo;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudiantes")
public class Estudiante extends Persona {

	@ManyToOne
	@JoinColumn(name = "id_grupo")
	private Grupo grupo;

	private String email;
	private String direccion;
	private String telefono;
	private String fechanac;
	private String dni;
	private LocalDateTime horario;
	private String curso;

	@OneToMany(mappedBy = "estudiante")
	private List<FormacionEmpresa> formaciones;

	@OneToMany(mappedBy = "estudiante")
	private List<Incidencia> incidencias;

	public Estudiante() {
		super();
	}

	public Estudiante(Grupo grupo, String email, String direccion,
			String telefono, String fechanac, String dni, LocalDateTime horario,
			String curso, List<FormacionEmpresa> formaciones,
			List<Incidencia> incidencias) {
		super();
		this.grupo = grupo;
		this.email = email;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fechanac = fechanac;
		this.dni = dni;
		this.horario = horario;
		this.curso = curso;
		this.formaciones = formaciones;
		this.incidencias = incidencias;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFechanac() {
		return fechanac;
	}

	public void setFechanac(String fechanac) {
		this.fechanac = fechanac;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public LocalDateTime getHorario() {
		return horario;
	}

	public void setHorario(LocalDateTime horario) {
		this.horario = horario;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public List<FormacionEmpresa> getFormaciones() {
		return formaciones;
	}

	public void setFormaciones(List<FormacionEmpresa> formaciones) {
		this.formaciones = formaciones;
	}

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	@Override
	public String toString() {
		return "Estudiante [grupo=" + grupo + ", email=" + email
				+ ", direccion=" + direccion + ", telefono=" + telefono
				+ ", fechanac=" + fechanac + ", dni=" + dni + ", horario="
				+ horario + ", curso=" + curso + ", formaciones=" + formaciones
				+ ", incidencias=" + incidencias + "]";
	}

}

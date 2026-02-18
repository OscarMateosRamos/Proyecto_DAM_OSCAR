/**
*Clase Profesor.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesores")
public class Profesor extends Persona {

	private String codigoProfesor;
	private String departamento;
	private String email;

	@Enumerated(EnumType.STRING)
	private Especialidad especialidad;

	private String horarioTutoria;
	private String aulaAsignada;
	private boolean esCoordinador;
	private String fechaIngreso;

	@ManyToMany
	@JoinTable(name = "profesor_curso", joinColumns = @JoinColumn(name = "id_profesor"), inverseJoinColumns = @JoinColumn(name = "id_curso"))
	private List<Curso> cursosAsignados;

	@OneToMany(mappedBy = "profesor")
	private List<FormacionEmpresa> formaciones;

	public Profesor() {
		super();
	}

	public Profesor(String codigoProfesor, String departamento, String email,
			Especialidad especialidad, String horarioTutoria,
			String aulaAsignada, boolean esCoordinador, String fechaIngreso,
			List<Curso> cursosAsignados, List<FormacionEmpresa> formaciones) {
		super();
		this.codigoProfesor = codigoProfesor;
		this.departamento = departamento;
		this.email = email;
		this.especialidad = especialidad;
		this.horarioTutoria = horarioTutoria;
		this.aulaAsignada = aulaAsignada;
		this.esCoordinador = esCoordinador;
		this.fechaIngreso = fechaIngreso;
		this.cursosAsignados = cursosAsignados;
		this.formaciones = formaciones;
	}

	public String getCodigoProfesor() {
		return codigoProfesor;
	}

	public void setCodigoProfesor(String codigoProfesor) {
		this.codigoProfesor = codigoProfesor;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public String getHorarioTutoria() {
		return horarioTutoria;
	}

	public void setHorarioTutoria(String horarioTutoria) {
		this.horarioTutoria = horarioTutoria;
	}

	public String getAulaAsignada() {
		return aulaAsignada;
	}

	public void setAulaAsignada(String aulaAsignada) {
		this.aulaAsignada = aulaAsignada;
	}

	public boolean isEsCoordinador() {
		return esCoordinador;
	}

	public void setEsCoordinador(boolean esCoordinador) {
		this.esCoordinador = esCoordinador;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public List<Curso> getCursosAsignados() {
		return cursosAsignados;
	}

	public void setCursosAsignados(List<Curso> cursosAsignados) {
		this.cursosAsignados = cursosAsignados;
	}

	public List<FormacionEmpresa> getFormaciones() {
		return formaciones;
	}

	public void setFormaciones(List<FormacionEmpresa> formaciones) {
		this.formaciones = formaciones;
	}

	@Override
	public String toString() {
		return "Profesor [codigoProfesor=" + codigoProfesor + ", departamento="
				+ departamento + ", email=" + email + ", especialidad="
				+ especialidad + ", horarioTutoria=" + horarioTutoria
				+ ", aulaAsignada=" + aulaAsignada + ", esCoordinador="
				+ esCoordinador + ", fechaIngreso=" + fechaIngreso
				+ ", cursosAsignados=" + cursosAsignados + ", formaciones="
				+ formaciones + "]";
	}

}

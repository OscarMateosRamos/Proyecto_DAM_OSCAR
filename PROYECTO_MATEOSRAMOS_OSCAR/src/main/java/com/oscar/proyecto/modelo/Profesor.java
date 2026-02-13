package com.oscar.proyecto.modelo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Profesores")
public class Profesor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_profesor")
	private Long idProfesor;

	@OneToOne
	@JoinColumn(name = "id_persona")
	private Persona persona;

	@Column(name = "codigo_profesor")
	private String codigoProfesor;

	@Column(name = "departamento")
	private String departamento;

	@Column(name = "email")
	private String email;

	@Column(name = "especialidad")
	private String especialidad;

	@Column(name = "horario_tutoria")
	private String horarioTutoria;

	@Column(name = "aula_asignada")
	private String aulaAsignada;

	@Column(name = "es_coordinador")
	private Boolean esCoordinador;

	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;

	public Profesor() {
		super();
	}

	public Profesor(Long idProfesor, Persona persona, String codigoProfesor,
			String departamento, String email, String especialidad,
			String horarioTutoria, String aulaAsignada, Boolean esCoordinador,
			Date fechaIngreso) {
		super();
		this.idProfesor = idProfesor;
		this.persona = persona;
		this.codigoProfesor = codigoProfesor;
		this.departamento = departamento;
		this.email = email;
		this.especialidad = especialidad;
		this.horarioTutoria = horarioTutoria;
		this.aulaAsignada = aulaAsignada;
		this.esCoordinador = esCoordinador;
		this.fechaIngreso = fechaIngreso;
	}

	public Long getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(Long idProfesor) {
		this.idProfesor = idProfesor;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
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

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
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

	public Boolean getEsCoordinador() {
		return esCoordinador;
	}

	public void setEsCoordinador(Boolean esCoordinador) {
		this.esCoordinador = esCoordinador;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	@Override
	public String toString() {
		return "Profesor [idProfesor=" + idProfesor + ", persona=" + persona
				+ ", codigoProfesor=" + codigoProfesor + ", departamento="
				+ departamento + ", email=" + email + ", especialidad="
				+ especialidad + ", horarioTutoria=" + horarioTutoria
				+ ", aulaAsignada=" + aulaAsignada + ", esCoordinador="
				+ esCoordinador + ", fechaIngreso=" + fechaIngreso + "]";
	}

}

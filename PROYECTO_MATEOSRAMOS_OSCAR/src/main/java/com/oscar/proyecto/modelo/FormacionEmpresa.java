
package com.oscar.proyecto.modelo;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "formacion_empresa")
public class FormacionEmpresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFormacion;

	@ManyToOne
	@JoinColumn(name = "id_estudiante")
	private Estudiante estudiante;

	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "id_tutor_empresa")
	private TutorEmpresa tutorEmpresa;

	@ManyToOne
	@JoinColumn(name = "id_profesor_coordinador")
	private Profesor profesorCoordinador;

	private Date fechaInicio;
	private Date fechaFin;

	@Enumerated(EnumType.STRING)
	private Periodo periodo;

	@OneToMany(mappedBy = "formacion")
	private List<Asistencia> asistencias;

	@OneToMany(mappedBy = "formacion")
	private List<Documento> documentos;

	@OneToMany(mappedBy = "formacion")
	private List<Incidencia> incidencias;

	@OneToOne(mappedBy = "formacion")
	private Evaluacion evaluacion;

	public FormacionEmpresa() {
	}

	public FormacionEmpresa(Long idFormacion, Estudiante estudiante,
			Empresa empresa, TutorEmpresa tutorEmpresa,
			Profesor profesorCoordinador, Date fechaInicio, Date fechaFin,
			Periodo periodo, List<Asistencia> asistencias,
			List<Documento> documentos, List<Incidencia> incidencias,
			Evaluacion evaluacion) {
		this.idFormacion = idFormacion;
		this.estudiante = estudiante;
		this.empresa = empresa;
		this.tutorEmpresa = tutorEmpresa;
		this.profesorCoordinador = profesorCoordinador;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.periodo = periodo;
		this.asistencias = asistencias;
		this.documentos = documentos;
		this.incidencias = incidencias;
		this.evaluacion = evaluacion;
	}

	public Long getIdFormacion() {
		return idFormacion;
	}

	public void setIdFormacion(Long idFormacion) {
		this.idFormacion = idFormacion;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TutorEmpresa getTutorEmpresa() {
		return tutorEmpresa;
	}

	public void setTutorEmpresa(TutorEmpresa tutorEmpresa) {
		this.tutorEmpresa = tutorEmpresa;
	}

	public Profesor getProfesorCoordinador() {
		return profesorCoordinador;
	}

	public void setProfesorCoordinador(Profesor profesorCoordinador) {
		this.profesorCoordinador = profesorCoordinador;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public Evaluacion getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	@Override
	public String toString() {
		return "FormacionEmpresa{" + "idFormacion=" + idFormacion
				+ ", estudiante=" + estudiante + ", empresa=" + empresa
				+ ", tutorEmpresa=" + tutorEmpresa + ", profesorCoordinador="
				+ profesorCoordinador + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", periodo=" + periodo
				+ ", asistencias=" + asistencias + ", documentos=" + documentos
				+ ", incidencias=" + incidencias + ", evaluacion=" + evaluacion
				+ '}';
	}
}

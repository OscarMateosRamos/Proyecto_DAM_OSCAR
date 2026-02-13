package com.oscar.proyecto.modelo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Incidencias")

public class Incidencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_incidencia")
	private Long idIncidencia;

	@ManyToOne
	@JoinColumn(name = "id_formacion")
	private FormacionEmpresa formacion;

	@ManyToOne
	@JoinColumn(name = "id_estudiante")
	private Estudiante estudiante;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha")
	private Date fecha;

	public Incidencia() {
		super();
	}

	public Incidencia(Long idIncidencia, FormacionEmpresa formacion,
			Estudiante estudiante, String descripcion, Date fecha) {
		super();
		this.idIncidencia = idIncidencia;
		this.formacion = formacion;
		this.estudiante = estudiante;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public Long getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(Long idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public FormacionEmpresa getFormacion() {
		return formacion;
	}

	public void setFormacion(FormacionEmpresa formacion) {
		this.formacion = formacion;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Incidencia [idIncidencia=" + idIncidencia + ", formacion="
				+ formacion + ", estudiante=" + estudiante + ", descripcion="
				+ descripcion + ", fecha=" + fecha + "]";
	}

}

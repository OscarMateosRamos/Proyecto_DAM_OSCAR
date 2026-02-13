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
@Table(name = "Evaluacion")
public class Evaluacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evaluacion")
	private Long idEvaluacion;

	@ManyToOne
	@JoinColumn(name = "id_formacion")
	private FormacionEmpresa formacion;

	@Column(name = "nota")
	private Double nota;

	@Column(name = "comentarios")
	private String comentarios;

	@Column(name = "fecha")
	private Date fecha;

	public Evaluacion() {
		super();
	}

	public Evaluacion(Long idEvaluacion, FormacionEmpresa formacion,
			Double nota, String comentarios, Date fecha) {
		super();
		this.idEvaluacion = idEvaluacion;
		this.formacion = formacion;
		this.nota = nota;
		this.comentarios = comentarios;
		this.fecha = fecha;
	}

	public Long getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

	public FormacionEmpresa getFormacion() {
		return formacion;
	}

	public void setFormacion(FormacionEmpresa formacion) {
		this.formacion = formacion;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Evaluacion [idEvaluacion=" + idEvaluacion + ", formacion="
				+ formacion + ", nota=" + nota + ", comentarios=" + comentarios
				+ ", fecha=" + fecha + "]";
	}

}

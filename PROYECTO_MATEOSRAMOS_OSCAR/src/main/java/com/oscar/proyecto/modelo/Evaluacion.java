/**
*Clase Evaluacion.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "evaluaciones")
public class Evaluacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEvaluacion;

	@OneToOne
	@JoinColumn(name = "id_formacion")
	private FormacionEmpresa formacion;

	private Double nota;
	private String comentarios;
	private String fecha;

	public Evaluacion() {
		super();
	}

	public Evaluacion(Long idEvaluacion, FormacionEmpresa formacion,
			Double nota, String comentarios, String fecha) {
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Evaluacion [idEvaluacion=" + idEvaluacion + ", formacion="
				+ formacion + ", nota=" + nota + ", comentarios=" + comentarios
				+ ", fecha=" + fecha + "]";
	}

}

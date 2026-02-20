/**
*Clase Asistencia.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.modelo;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "asistencia")
public class Asistencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAsistencia;

	@ManyToOne
	@JoinColumn(name = "id_formacion")
	private FormacionEmpresa formacion;

	private Date fecha;

	@Enumerated(EnumType.STRING)
	private TipoAsistencia tipo;

	@Enumerated(EnumType.STRING)
	private Justificada justificada;

	public Asistencia() {
		super();
	}

	public Asistencia(Long idAsistencia, FormacionEmpresa formacion, Date fecha,
			TipoAsistencia tipo, Justificada justificada) {
		super();
		this.idAsistencia = idAsistencia;
		this.formacion = formacion;
		this.fecha = fecha;
		this.tipo = tipo;
		this.justificada = justificada;
	}

	public Long getIdAsistencia() {
		return idAsistencia;
	}

	public void setIdAsistencia(Long idAsistencia) {
		this.idAsistencia = idAsistencia;
	}

	public FormacionEmpresa getFormacion() {
		return formacion;
	}

	public void setFormacion(FormacionEmpresa formacion) {
		this.formacion = formacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public TipoAsistencia getTipo() {
		return tipo;
	}

	public void setTipo(TipoAsistencia tipo) {
		this.tipo = tipo;
	}

	public Justificada getJustificada() {
		return justificada;
	}

	public void setJustificada(Justificada justificada) {
		this.justificada = justificada;
	}

	@Override
	public String toString() {
		return "Asistencia [idAsistencia=" + idAsistencia + ", formacion="
				+ formacion + ", fecha=" + fecha + ", tipo=" + tipo
				+ ", justificada=" + justificada + "]";
	}

}

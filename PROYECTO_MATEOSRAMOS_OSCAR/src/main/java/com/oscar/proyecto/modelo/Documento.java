/**
*Clase Documento.java
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "documentos")
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDocumento;

	@ManyToOne
	@JoinColumn(name = "id_formacion")
	private FormacionEmpresa formacion;

	private String nombreArchivo;
	private String fechaSubida;

	public Documento() {
		super();
	}

	public Documento(Long idDocumento, FormacionEmpresa formacion,
			String nombreArchivo, String fechaSubida) {
		super();
		this.idDocumento = idDocumento;
		this.formacion = formacion;
		this.nombreArchivo = nombreArchivo;
		this.fechaSubida = fechaSubida;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public FormacionEmpresa getFormacion() {
		return formacion;
	}

	public void setFormacion(FormacionEmpresa formacion) {
		this.formacion = formacion;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(String fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

	@Override
	public String toString() {
		return "Documento [idDocumento=" + idDocumento + ", formacion="
				+ formacion + ", nombreArchivo=" + nombreArchivo
				+ ", fechaSubida=" + fechaSubida + "]";
	}

}

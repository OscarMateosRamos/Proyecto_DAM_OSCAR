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
@Table(name = "Documentos")

public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long idDocumento;

    @ManyToOne
    @JoinColumn(name = "id_formacion")
    private FormacionEmpresa formacion;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Column(name = "ruta")
    private String ruta;

    @Column(name = "fecha_subida")
    private Date fechaSubida;

	public Documento() {
		super();
	}

	public Documento(Long idDocumento, FormacionEmpresa formacion,
			String nombreArchivo, String ruta, Date fechaSubida) {
		super();
		this.idDocumento = idDocumento;
		this.formacion = formacion;
		this.nombreArchivo = nombreArchivo;
		this.ruta = ruta;
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

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Date getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(Date fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

	@Override
	public String toString() {
		return "Documento [idDocumento=" + idDocumento + ", formacion="
				+ formacion + ", nombreArchivo=" + nombreArchivo + ", ruta="
				+ ruta + ", fechaSubida=" + fechaSubida + "]";
	}
    
    
    
}

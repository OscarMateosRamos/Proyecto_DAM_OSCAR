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
@Table(name = "Asistencia")

public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long idAsistencia;

    @ManyToOne
    @JoinColumn(name = "id_formacion")
    private FormacionEmpresa formacion;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "justificada")
    private Boolean justificada;

	public Asistencia() {
		super();
	}

	public Asistencia(Long idAsistencia, FormacionEmpresa formacion, Date fecha,
			String tipo, Boolean justificada) {
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getJustificada() {
		return justificada;
	}

	public void setJustificada(Boolean justificada) {
		this.justificada = justificada;
	}

	@Override
	public String toString() {
		return "Asistencia [idAsistencia=" + idAsistencia + ", formacion="
				+ formacion + ", fecha=" + fecha + ", tipo=" + tipo
				+ ", justificada=" + justificada + "]";
	}
    
    
    
}

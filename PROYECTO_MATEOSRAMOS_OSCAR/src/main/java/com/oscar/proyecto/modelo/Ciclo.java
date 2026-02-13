package com.oscar.proyecto.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Ciclos")

public class Ciclo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ciclo")
	private Long idCiclo;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	public Ciclo() {
		super();
	}

	public Ciclo(Long idCiclo, String nombre, String descripcion) {
		super();
		this.idCiclo = idCiclo;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Long getIdCiclo() {
		return idCiclo;
	}

	public void setIdCiclo(Long idCiclo) {
		this.idCiclo = idCiclo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Ciclo [idCiclo=" + idCiclo + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + "]";
	}

}

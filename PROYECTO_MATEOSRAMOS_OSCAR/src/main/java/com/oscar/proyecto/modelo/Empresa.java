package com.oscar.proyecto.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Empresas")
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Long idEmpresa;

	@Column(name = "nombre")
	private String nombre;

	public Empresa() {
		super();
	}

	public Empresa(Long idEmpresa, String nombre) {
		super();
		this.idEmpresa = idEmpresa;
		this.nombre = nombre;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", nombre=" + nombre + "]";
	}

}

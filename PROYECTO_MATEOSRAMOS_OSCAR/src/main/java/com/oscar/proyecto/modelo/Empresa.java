/**
*Clase Empresa.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresas")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEmpresa;

	private String nombre;

	@OneToMany(mappedBy = "empresa")
	private List<TutorEmpresa> tutores;

	@OneToMany(mappedBy = "empresa")
	private List<FormacionEmpresa> formaciones;

	public Empresa() {
		super();
	}

	public Empresa(Long idEmpresa, String nombre, List<TutorEmpresa> tutores,
			List<FormacionEmpresa> formaciones) {
		super();
		this.idEmpresa = idEmpresa;
		this.nombre = nombre;
		this.tutores = tutores;
		this.formaciones = formaciones;
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

	public List<TutorEmpresa> getTutores() {
		return tutores;
	}

	public void setTutores(List<TutorEmpresa> tutores) {
		this.tutores = tutores;
	}

	public List<FormacionEmpresa> getFormaciones() {
		return formaciones;
	}

	public void setFormaciones(List<FormacionEmpresa> formaciones) {
		this.formaciones = formaciones;
	}

	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", nombre=" + nombre
				+ ", tutores=" + tutores + ", formaciones=" + formaciones + "]";
	}

}

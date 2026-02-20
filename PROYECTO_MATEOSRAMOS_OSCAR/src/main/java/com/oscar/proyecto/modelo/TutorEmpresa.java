/**
*Clase TutorEmpresa.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tutores_empresa")
public class TutorEmpresa extends Persona {

	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	private String email;
	private String telefono;
	private String fechaIngreso;

	@OneToMany(mappedBy = "tutorEmpresa")
	private List<FormacionEmpresa> formaciones;

	public TutorEmpresa() {
		super();
	}

	public TutorEmpresa(Empresa empresa, String email, String telefono,
			String fechaIngreso, List<FormacionEmpresa> formaciones) {
		super();
		this.empresa = empresa;
		this.email = email;
		this.telefono = telefono;
		this.fechaIngreso = fechaIngreso;
		this.formaciones = formaciones;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public List<FormacionEmpresa> getFormaciones() {
		return formaciones;
	}

	public void setFormaciones(List<FormacionEmpresa> formaciones) {
		this.formaciones = formaciones;
	}

	@Override
	public String toString() {
		return "TutorEmpresa [empresa=" + empresa + ", email=" + email
				+ ", telefono=" + telefono + ", fechaIngreso=" + fechaIngreso
				+ ", formaciones=" + formaciones + "]";
	}

}

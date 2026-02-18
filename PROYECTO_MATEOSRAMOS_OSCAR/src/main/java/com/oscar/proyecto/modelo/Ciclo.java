/**
*Clase Ciclo.java
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
@Table(name = "ciclos")
public class Ciclo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCiclo;

	private String nombre;
	private String descripcion;

	@OneToMany(mappedBy = "ciclo")
	private List<Curso> cursos;

	public Ciclo() {
		super();
	}

	public Ciclo(Long idCiclo, String nombre, String descripcion,
			List<Curso> cursos) {
		super();
		this.idCiclo = idCiclo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cursos = cursos;
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

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	@Override
	public String toString() {
		return "Ciclo [idCiclo=" + idCiclo + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + ", cursos=" + cursos + "]";
	}

}

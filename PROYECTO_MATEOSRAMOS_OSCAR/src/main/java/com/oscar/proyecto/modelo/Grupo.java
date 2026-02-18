/**
*Clase Grupo.java
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "grupos")
public class Grupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGrupo;

	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;

	private String nombre;

	@OneToMany(mappedBy = "grupo")
	private List<Estudiante> estudiantes;

	public Grupo() {
		super();
	}

	public Grupo(Long idGrupo, Curso curso, String nombre,
			List<Estudiante> estudiantes) {
		super();
		this.idGrupo = idGrupo;
		this.curso = curso;
		this.nombre = nombre;
		this.estudiantes = estudiantes;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	@Override
	public String toString() {
		return "Grupo [idGrupo=" + idGrupo + ", curso=" + curso + ", nombre="
				+ nombre + ", estudiantes=" + estudiantes + "]";
	}

}

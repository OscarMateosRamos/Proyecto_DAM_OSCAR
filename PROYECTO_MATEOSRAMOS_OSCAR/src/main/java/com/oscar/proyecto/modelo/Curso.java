/**
*Clase Curso.java
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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCurso;

	@ManyToOne
	@JoinColumn(name = "id_ciclo")
	private Ciclo ciclo;

	private String nombre;
	private String codigo;
	private String añoAcademico;

	@OneToMany(mappedBy = "curso")
	private List<Grupo> grupos;

	@ManyToMany(mappedBy = "cursosAsignados")
	private List<Profesor> profesores;

	public Curso() {
		super();
	}

	public Curso(Long idCurso, Ciclo ciclo, String nombre, String codigo,
			String añoAcademico, List<Grupo> grupos,
			List<Profesor> profesores) {
		super();
		this.idCurso = idCurso;
		this.ciclo = ciclo;
		this.nombre = nombre;
		this.codigo = codigo;
		this.añoAcademico = añoAcademico;
		this.grupos = grupos;
		this.profesores = profesores;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public Ciclo getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getAñoAcademico() {
		return añoAcademico;
	}

	public void setAñoAcademico(String añoAcademico) {
		this.añoAcademico = añoAcademico;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(List<Profesor> profesores) {
		this.profesores = profesores;
	}

	@Override
	public String toString() {
		return "Curso [idCurso=" + idCurso + ", ciclo=" + ciclo + ", nombre="
				+ nombre + ", codigo=" + codigo + ", añoAcademico="
				+ añoAcademico + ", grupos=" + grupos + ", profesores="
				+ profesores + "]";
	}

}

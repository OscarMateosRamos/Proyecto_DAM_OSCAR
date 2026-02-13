package com.oscar.proyecto.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Grupos")
public class Grupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_grupo")
	private Long idGrupo;

	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;

	@Column(name = "nombre")
	private String nombre;

	public Grupo() {
		super();
	}

	public Grupo(Long idGrupo, Curso curso, String nombre) {
		super();
		this.idGrupo = idGrupo;
		this.curso = curso;
		this.nombre = nombre;
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

	@Override
	public String toString() {
		return "Grupo [idGrupo=" + idGrupo + ", curso=" + curso + ", nombre="
				+ nombre + "]";
	}

}

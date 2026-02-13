package com.oscar.proyecto.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Cursos")

public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_curso")
	private Long idCurso;

	@ManyToOne
	@JoinColumn(name = "id_ciclo")
	private Ciclo ciclo;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "año_academico")
	private String anoAcademico;

	public Curso() {
		super();
	}

	public Curso(Long idCurso, Ciclo ciclo, String nombre, String codigo,
			String anoAcademico) {
		super();
		this.idCurso = idCurso;
		this.ciclo = ciclo;
		this.nombre = nombre;
		this.codigo = codigo;
		this.anoAcademico = anoAcademico;
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

	public String getAnoAcademico() {
		return anoAcademico;
	}

	public void setAnoAcademico(String anoAcademico) {
		this.anoAcademico = anoAcademico;
	}

	@Override
	public String toString() {
		return "Curso [idCurso=" + idCurso + ", ciclo=" + ciclo + ", nombre="
				+ nombre + ", codigo=" + codigo + ", anoAcademico="
				+ anoAcademico + "]";
	}

}

package com.oscar.proyecto.modelo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Estudiantes")
public class Estudiante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_estudiante")
	private Long idEstudiante;

	@OneToOne
	@JoinColumn(name = "id_persona")
	private Persona persona;

	@ManyToOne
	@JoinColumn(name = "id_grupo")
	private Grupo grupo;

	@Column(name = "email")
	private String email;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "fechanac")
	private Date fechaNacimiento;

	@Column(name = "dni")
	private String dni;

	@Column(name = "horario")
	private String horario;

	@Column(name = "curso")
	private String curso;

	public Estudiante() {
		super();
	}

	public Estudiante(Long idEstudiante, Persona persona, Grupo grupo,
			String email, String direccion, String telefono,
			Date fechaNacimiento, String dni, String horario, String curso) {
		super();
		this.idEstudiante = idEstudiante;
		this.persona = persona;
		this.grupo = grupo;
		this.email = email;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.dni = dni;
		this.horario = horario;
		this.curso = curso;
	}

	public Long getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "Estudiante [idEstudiante=" + idEstudiante + ", persona="
				+ persona + ", grupo=" + grupo + ", email=" + email
				+ ", direccion=" + direccion + ", telefono=" + telefono
				+ ", fechaNacimiento=" + fechaNacimiento + ", dni=" + dni
				+ ", horario=" + horario + ", curso=" + curso + "]";
	}

}

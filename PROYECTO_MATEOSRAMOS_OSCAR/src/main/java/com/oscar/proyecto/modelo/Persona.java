package com.oscar.proyecto.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Personas")
public class Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona")
	private Long idPersona;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "usuario")
	private String usuario;

	@Column(name = "contraseña")
	private String contrasena;

	@Enumerated(EnumType.STRING)
	@Column(name = "perfil")
	private Perfil perfil;

	public Persona() {
		super();
	}

	public Persona(Long idPersona, String nombre, String apellidos,
			String usuario, String contrasena, Perfil perfil) {
		super();
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.perfil = perfil;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "Persona [idPersona=" + idPersona + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", usuario=" + usuario
				+ ", contrasena=" + contrasena + ", perfil=" + perfil + "]";
	}

}

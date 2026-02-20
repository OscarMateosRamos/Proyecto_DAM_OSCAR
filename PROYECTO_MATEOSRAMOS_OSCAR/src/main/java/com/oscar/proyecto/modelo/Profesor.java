/**
 * Clase Profesor.java
 *
 * Representa a un profesor del centro educativo.
 * Ahora coordina las formaciones en empresa mediante la relación
 * profesorCoordinador en FormacionEmpresa.
 *
 * @author Oscar
 */
package com.oscar.proyecto.modelo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesores")
public class Profesor extends Persona {

    private String codigoProfesor;
    private String departamento;
    private String email;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    private LocalDateTime horarioTutoria;
    private String aulaAsignada;
    private boolean esCoordinador;
    private Date fechaIngreso;

    @ManyToMany
    @JoinTable(
        name = "profesor_curso",
        joinColumns = @JoinColumn(name = "id_profesor"),
        inverseJoinColumns = @JoinColumn(name = "id_curso")
    )
    private List<Curso> cursosAsignados;

  
    @OneToMany(mappedBy = "profesorCoordinador")
    private List<FormacionEmpresa> formacionesCoordinadas;

    public Profesor() {
        super();
    }

    public Profesor(String codigoProfesor, String departamento, String email,
                    Especialidad especialidad, LocalDateTime horarioTutoria,
                    String aulaAsignada, boolean esCoordinador, Date fechaIngreso,
                    List<Curso> cursosAsignados, List<FormacionEmpresa> formacionesCoordinadas) {
        super();
        this.codigoProfesor = codigoProfesor;
        this.departamento = departamento;
        this.email = email;
        this.especialidad = especialidad;
        this.horarioTutoria = horarioTutoria;
        this.aulaAsignada = aulaAsignada;
        this.esCoordinador = esCoordinador;
        this.fechaIngreso = fechaIngreso;
        this.cursosAsignados = cursosAsignados;
        this.formacionesCoordinadas = formacionesCoordinadas;
    }

    public String getCodigoProfesor() {
        return codigoProfesor;
    }

    public void setCodigoProfesor(String codigoProfesor) {
        this.codigoProfesor = codigoProfesor;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDateTime getHorarioTutoria() {
        return horarioTutoria;
    }

    public void setHorarioTutoria(LocalDateTime horarioTutoria) {
        this.horarioTutoria = horarioTutoria;
    }

    public String getAulaAsignada() {
        return aulaAsignada;
    }

    public void setAulaAsignada(String aulaAsignada) {
        this.aulaAsignada = aulaAsignada;
    }

    public boolean isEsCoordinador() {
        return esCoordinador;
    }

    public void setEsCoordinador(boolean esCoordinador) {
        this.esCoordinador = esCoordinador;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public List<Curso> getCursosAsignados() {
        return cursosAsignados;
    }

    public void setCursosAsignados(List<Curso> cursosAsignados) {
        this.cursosAsignados = cursosAsignados;
    }

    public List<FormacionEmpresa> getFormacionesCoordinadas() {
        return formacionesCoordinadas;
    }

    public void setFormacionesCoordinadas(List<FormacionEmpresa> formacionesCoordinadas) {
        this.formacionesCoordinadas = formacionesCoordinadas;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellidos() + " - " + codigoProfesor;
    }
}

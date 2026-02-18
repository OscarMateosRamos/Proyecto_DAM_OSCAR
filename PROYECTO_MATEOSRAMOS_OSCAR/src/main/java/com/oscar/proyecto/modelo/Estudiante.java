/**
*Clase Estudiante.java
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
@Table(name = "estudiantes")
public class Estudiante extends Persona {

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    private String email;
    private String direccion;
    private String telefono;
    private String fechanac;
    private String dni;
    private String horario;
    private String curso;

    @OneToMany(mappedBy = "estudiante")
    private List<FormacionEmpresa> formaciones;

    @OneToMany(mappedBy = "estudiante")
    private List<Incidencia> incidencias;
    
    
    
    
}

/**
*Clase FormacionEmpresa.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "formacion_empresa")
public class FormacionEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFormacion;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "id_tutor")
    private TutorEmpresa tutor;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;

    private String fechaInicio;
    private String fechaFin;

    @Enumerated(EnumType.STRING)
    private Periodo periodo; 

    @OneToMany(mappedBy = "formacion")
    private List<Asistencia> asistencias;

    @OneToMany(mappedBy = "formacion")
    private List<Documento> documentos;

    @OneToMany(mappedBy = "formacion")
    private List<Incidencia> incidencias;

    @OneToOne(mappedBy = "formacion")
    private Evaluacion evaluacion;
}


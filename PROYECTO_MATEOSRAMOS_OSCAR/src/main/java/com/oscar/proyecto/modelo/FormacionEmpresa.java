package com.oscar.proyecto.modelo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "FormacionEmpresa")
public class FormacionEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formacion")
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

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "periodo")
    private String periodo;
}

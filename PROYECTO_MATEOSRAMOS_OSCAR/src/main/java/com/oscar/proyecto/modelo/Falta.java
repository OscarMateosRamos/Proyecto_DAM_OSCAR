package com.oscar.proyecto.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "falta")
public class Falta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFalta;

    @ManyToOne
    @JoinColumn(name = "id_formacion")
    private FormacionEmpresa formacion;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private TipoFalta tipo;

    @Enumerated(EnumType.STRING)
    private Justificada justificada;

    private String justificante;

    public Falta() {}

    public Long getIdFalta() {
        return idFalta;
    }

    public void setIdFalta(Long idFalta) {
        this.idFalta = idFalta;
    }

    public FormacionEmpresa getFormacion() {
        return formacion;
    }

    public void setFormacion(FormacionEmpresa formacion) {
        this.formacion = formacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoFalta getTipo() {
        return tipo;
    }

    public void setTipo(TipoFalta tipo) {
        this.tipo = tipo;
    }

    public Justificada getJustificada() {
        return justificada;
    }

    public void setJustificada(Justificada justificada) {
        this.justificada = justificada;
    }

    public String getJustificante() {
        return justificante;
    }

    public void setJustificante(String justificante) {
        this.justificante = justificante;
    }
}
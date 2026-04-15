package com.oscar.proyecto.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Perfil;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.repositorios.PersonaRepository;

@Service
public class ServicioSesion {

    private Persona usuarioActual;

    private String nombrePersonaActual;
    private Perfil perfilActual;
    
    @Autowired
    PersonaRepository personaRepository;

   
    public Persona getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Persona usuarioActual) {
        this.usuarioActual = usuarioActual;
        this.nombrePersonaActual = usuarioActual.getNombre(); 
        this.perfilActual = usuarioActual.getPerfil();       
    }

   
    public String getNombrePersonaActual() {
        return nombrePersonaActual;
    }

    public void setNombrePersonaActual(String nombrePersonaActual) {
        this.nombrePersonaActual = nombrePersonaActual;
    }

    public Perfil getPerfil() {
        return perfilActual;
    }

    public void setPerfil(Perfil perfilActual) {
        this.perfilActual = perfilActual;
    }

    public void limpiarSesion() {
        this.usuarioActual = null;
        this.nombrePersonaActual = null;
        this.perfilActual = null;
    }

    public boolean isAdmin() {
        return perfilActual == Perfil.ADMIN;
    }

    public boolean isProfesor() {
        return perfilActual == Perfil.PROFESOR;
    }

    public boolean isTutor() {
        return perfilActual == Perfil.TUTOR;
    }

    public boolean isEstudiante() {
        return perfilActual == Perfil.ESTUDIANTE;
    }

    public Optional<Persona> getPersonaActual() {
        
        String usuario = getNombrePersonaActual(); 

        if (usuario == null) {
            return null;
        }

        
        return personaRepository.findByUsuario(usuario);
    }
}

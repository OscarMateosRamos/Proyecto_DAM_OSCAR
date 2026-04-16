package com.oscar.proyecto.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oscar.proyecto.modelo.Estudiante;
import com.oscar.proyecto.repositorios.EstudianteRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ServicioEstudiante {

    @Autowired
    private EstudianteRepository estudianteRepositorio;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Estudiante crearEstudiante(Estudiante e) {
        Estudiante merged = entityManager.merge(e);
        entityManager.flush();    
        entityManager.refresh(merged); 
        return merged;
    }

    public Estudiante guardarEstudiante(Estudiante e) {
        return estudianteRepositorio.save(e);
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteRepositorio.findAll();
    }

    public void eliminarEstudiante(Long idPersona) {
        Estudiante e = estudianteRepositorio.findById(idPersona)
                .orElseThrow(() -> new IllegalStateException("Estudiante no encontrado"));
        if (!e.getFormaciones().isEmpty()) {
            throw new IllegalStateException(
                    "No se puede eliminar este estudiante porque participa en una formacion.");
        }
        estudianteRepositorio.deleteById(idPersona);
    }

    public Estudiante obtenerEstudianteCompleto(String usuario) {
        return estudianteRepositorio.buscarEstudianteCompleto(usuario.toLowerCase());
    }

    public Estudiante obtenerEstudiantePorPersonaId(Long idPersona) {
        return estudianteRepositorio.buscarPorPersonaId(idPersona);
    }
}
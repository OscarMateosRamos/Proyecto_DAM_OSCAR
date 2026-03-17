/**
 * Clase ServicioPersona.java
 *
 * @author Oscar Mateos Ramos
 * @version
 */
package com.oscar.proyecto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Estudiante;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.repositorios.EstudianteRepository;
import com.oscar.proyecto.repositorios.PersonaRepository;

@Service
public class ServicioPersona {

    @Autowired
    private PersonaRepository personaRepositorio;
    
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    public List<Persona> listarPersonas() {
        return personaRepositorio.findAll();
    }

    public void eliminarPersona(Long idPersona) {

        Optional<Estudiante> estudianteOpt = estudianteRepository.findById(idPersona);

        if (estudianteOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();

            
            if (!estudiante.getFormaciones().isEmpty()) {
                throw new IllegalStateException(
                    "No se puede eliminar: el estudiante tiene formaciones asociadas."
                );
            }
        }

       
       personaRepositorio.deleteById(idPersona);
    }

    public void guardarPersona(Persona nuevaPersona) {
        
        if (nuevaPersona.getContraseña() != null && !nuevaPersona.getContraseña().isEmpty()) {
            String contraseñaCifrada = passwordEncoderService.encode(nuevaPersona.getContraseña());
            nuevaPersona.setContraseña(contraseñaCifrada);
        }
        personaRepositorio.saveAndFlush(nuevaPersona);
    }
}

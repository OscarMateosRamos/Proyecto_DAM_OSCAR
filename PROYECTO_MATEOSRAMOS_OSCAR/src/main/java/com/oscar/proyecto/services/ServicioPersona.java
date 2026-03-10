/**
 * Clase ServicioPersona.java
 *
 * @author Oscar Mateos Ramos
 * @version
 */
package com.oscar.proyecto.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.repositorios.PersonaRepository;

@Service
public class ServicioPersona {

    @Autowired
    private PersonaRepository personaRepositorio;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    public List<Persona> listarPersonas() {
        return personaRepositorio.findAll();
    }

    public void eliminarPersona(Long idPersona) {
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

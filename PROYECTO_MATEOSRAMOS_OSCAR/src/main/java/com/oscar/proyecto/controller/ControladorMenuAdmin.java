package com.oscar.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.services.ServicioSesion;

import javafx.fxml.FXML;

@Component
public class ControladorMenuAdmin {

    @Autowired
    private StageManager stageManager;

    @Autowired
    private ServicioSesion sesionServicio;

   

    @FXML
    private void abrirGestionProfesores() {
        
    }

    @FXML
    private void abrirGestionCursos() {
       
    }

    @FXML
    private void abrirGestionEstudiantes() {
       
    }

    @FXML
    private void abrirGestionEmpresas() {
        
    }

    @FXML
    private void abrirGestionTutores() {
        
    }

    @FXML
    private void abrirAsignacion() {
       
    }

   

    @FXML
    private void cerrarSesion() {
        sesionServicio.limpiarSesion();
        stageManager.switchScene(FxmlView.LOGIN);
    }
}

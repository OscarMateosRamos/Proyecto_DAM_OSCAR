package com.oscar.proyecto.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.FormacionEmpresa;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorDetallesFormacion {

    @FXML private TextField idFormacionField;
    @FXML private TextField estudianteField;
    @FXML private TextField empresaField;
    @FXML private TextField tutorEmpresaField;
    @FXML private TextField profesorCoordinadorField;
    @FXML private DatePicker fechaInicioPicker;
    @FXML private DatePicker fechaFinPicker;
    @FXML private TextField periodoField;
    @FXML private ListView<String> faltasListView;
    @FXML private ListView<String> documentosListView;
    @FXML private ListView<String> incidenciasListView;
    @FXML private TextArea evaluacionTextArea;
    @FXML private Button cerrarButton;
    
    
    @Autowired
    private StageManager stageManager;

    private FormacionEmpresa formacion;

    public void setFormacion(FormacionEmpresa formacion) {
        this.formacion = formacion;
        cargarDatos();
    }

    private void cargarDatos() {
        idFormacionField.setText(formacion.getIdFormacion().toString());
        estudianteField.setText(formacion.getEstudiante().toString());
        empresaField.setText(formacion.getEmpresa().toString());
        tutorEmpresaField.setText(formacion.getTutorEmpresa().toString());
        profesorCoordinadorField.setText(formacion.getProfesorCoordinador().toString());
        fechaInicioPicker.setValue(formacion.getFechaInicio().toLocalDate());
        fechaFinPicker.setValue(formacion.getFechaFin().toLocalDate());
        periodoField.setText(formacion.getPeriodo().toString());

        
        faltasListView.getItems().addAll(
            formacion.getFaltas().stream()
                .map(falta -> falta.toString())
                .collect(Collectors.toList())
        );
       
    }

    @FXML
    private void cerrarVentana() {
       stageManager.switchScene(FxmlView.MENUESTUDIANTE);
    }
}
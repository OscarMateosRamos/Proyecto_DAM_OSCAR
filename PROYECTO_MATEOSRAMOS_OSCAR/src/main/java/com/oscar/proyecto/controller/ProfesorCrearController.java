package com.oscar.proyecto.controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Especialidad;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.modelo.Profesor;
import com.oscar.proyecto.services.ServicioPersona;
import com.oscar.proyecto.services.ServicioProfesor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

@Controller
public class ProfesorCrearController {

    @Autowired
    private ServicioProfesor profesorServicio;

    @Autowired
    private ServicioPersona personaServicio;

    @Autowired
    private StageManager stageManager;

    @FXML
    private ComboBox<Persona> comboPersona;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDepartamento;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<Especialidad> comboEspecialidad;

    @FXML
    private TextField txtAula;

    @FXML
    private CheckBox checkCoordinador;

    @FXML
    private DatePicker dateIngreso;

    @FXML
    private TextField txtHorario;

    @FXML
    private VBox rootPane;

    @FXML
    public void initialize() {
        comboEspecialidad.getItems().setAll(Especialidad.values());
        comboPersona.getItems().setAll(personaServicio.listarPersonas());
        cargarEstilos();
    }

    @FXML
    private void crearProfesor() {
        try {
            if (comboPersona.getValue() == null ||
                txtCodigo.getText().isEmpty() ||
                txtDepartamento.getText().isEmpty() ||
                txtEmail.getText().isEmpty() ||
                comboEspecialidad.getValue() == null ||
                txtAula.getText().isEmpty() ||
                dateIngreso.getValue() == null ||
                txtHorario.getText().isEmpty()) {

                mostrarAlerta("Todos los campos son obligatorios.");
                return;
            }

            Persona persona = comboPersona.getValue();

            LocalTime hora = LocalTime.parse(txtHorario.getText());
            LocalDate fecha = dateIngreso.getValue();
            LocalDateTime horarioTutoria = LocalDateTime.of(LocalDate.now(), hora);

            Profesor profesor = new Profesor();
            profesor.setIdPersona(persona.getIdPersona());
            profesor.setCodigoProfesor(txtCodigo.getText());
            profesor.setDepartamento(txtDepartamento.getText());
            profesor.setEmail(txtEmail.getText());
            profesor.setEspecialidad(comboEspecialidad.getValue());
            profesor.setAulaAsignada(txtAula.getText());
            profesor.setEsCoordinador(checkCoordinador.isSelected());
            profesor.setFechaIngreso(Date.valueOf(fecha));
            profesor.setHorarioTutoria(horarioTutoria);

            profesorServicio.guardarProfesor(profesor);

            mostrarAlerta("Profesor creado correctamente.");
            volverGestionProfesores();

        } catch (Exception e) {
            mostrarAlerta("Error al crear el profesor: " + e.getMessage());
        }
    }

    @FXML
    private void volverGestionProfesores() {
        stageManager.switchScene(FxmlView.GESTIONPROFESORES);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarEstilos() {
        try {
            String cssPath = "/styles/ProfesorCrear.css";
            URL cssUrl = getClass().getResource(cssPath);
            if (cssUrl != null) {
                rootPane.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("CSS NO encontrado en: " + cssPath);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el CSS: " + e.getMessage());
        }
    }
}

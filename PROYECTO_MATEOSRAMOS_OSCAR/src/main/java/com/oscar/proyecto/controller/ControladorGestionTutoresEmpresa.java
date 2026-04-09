package com.oscar.proyecto.controller;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Empresa;
import com.oscar.proyecto.modelo.Perfil;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.modelo.TutorEmpresa;
import com.oscar.proyecto.services.ServicioEmpresa;
import com.oscar.proyecto.services.ServicioPersona;
import com.oscar.proyecto.services.ServicioTutorEmpresa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

@Component
public class ControladorGestionTutoresEmpresa {

    @Autowired
    private StageManager stageManager;

    @Autowired
    private ServicioTutorEmpresa tutorServicio;

    @Autowired
    private ServicioPersona personaServicio;

    @Autowired
    private ServicioEmpresa empresaServicio;

    @FXML
    private TableView<TutorEmpresa> tablaTutores;

    @FXML
    private TableColumn<TutorEmpresa, Long> colCodigo;

    @FXML
    private TableColumn<TutorEmpresa, String> colEmpresa;

    @FXML
    private TableColumn<TutorEmpresa, String> colEmail;

    @FXML
    private TableColumn<TutorEmpresa, String> colTelefono;

    @FXML
    private TableColumn<TutorEmpresa, Date> colFechaIngreso;

    private ObservableList<TutorEmpresa> listaTutores;

    @FXML
    private BorderPane rootPane;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarTutores();
        cargarEstilos();
    }

    private void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idPersona"));

        colEmpresa.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEmpresa() != null ? data.getValue().getEmpresa().getNombre() : ""
            )
        );

        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
    }

    private void cargarTutores() {
        List<TutorEmpresa> tutores = tutorServicio.listarTutores();
        listaTutores = FXCollections.observableArrayList(tutores);
        tablaTutores.setItems(listaTutores);
    }

    
    @FXML
    private void añadirTutor() {

        Dialog<TutorEmpresa> dialog = new Dialog<>();
        dialog.setTitle("Añadir Tutor de Empresa");
        dialog.setHeaderText("Introduce los datos del nuevo tutor:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Persona> comboPersona = new ComboBox<>();
        comboPersona.getItems().setAll(
            personaServicio.listarPersonas().stream()
                .filter(p -> p.getPerfil() == Perfil.TUTOR)
                .toList()
        );
        comboPersona.setPromptText("Selecciona persona");


        ComboBox<Empresa> comboEmpresa = new ComboBox<>();
        comboEmpresa.getItems().setAll(empresaServicio.listarEmpresas());
        comboEmpresa.setPromptText("Selecciona empresa");

        TextField email = new TextField();
        email.setPromptText("Email");

        TextField telefono = new TextField();
        telefono.setPromptText("Teléfono");

        DatePicker fechaIngreso = new DatePicker();

        grid.add(new Label("Persona:"), 0, 0);
        grid.add(comboPersona, 1, 0);

        grid.add(new Label("Empresa:"), 0, 1);
        grid.add(comboEmpresa, 1, 1);

        grid.add(new Label("Email:"), 0, 2);
        grid.add(email, 1, 2);

        grid.add(new Label("Teléfono:"), 0, 3);
        grid.add(telefono, 1, 3);

        grid.add(new Label("Fecha ingreso:"), 0, 4);
        grid.add(fechaIngreso, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {

                TutorEmpresa tutor = new TutorEmpresa();

                Persona p = comboPersona.getValue();
                tutor.setIdPersona(p.getIdPersona());
                tutor.setNombre(p.getNombre());
                tutor.setApellidos(p.getApellidos());

                tutor.setEmpresa(comboEmpresa.getValue());
                tutor.setEmail(email.getText());
                tutor.setTelefono(telefono.getText());
                tutor.setFechaIngreso(Date.valueOf(fechaIngreso.getValue()));

                return tutor;
            }
            return null;
        });

        Optional<TutorEmpresa> result = dialog.showAndWait();

        result.ifPresent(tutor -> {
            tutorServicio.guardarTutor(tutor);
            listaTutores.add(tutor);
            tablaTutores.refresh();
        });
    }

    
    @FXML
    private void editarTutor() {
        TutorEmpresa seleccionado = tablaTutores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un tutor", "Debes seleccionar un tutor para editarlo.");
            return;
        }

        Dialog<TutorEmpresa> dialog = new Dialog<>();
        dialog.setTitle("Editar Tutor de Empresa");
        dialog.setHeaderText("Modifica los datos del tutor:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombre = new TextField(seleccionado.getNombre());
        TextField apellidos = new TextField(seleccionado.getApellidos());

        ComboBox<Empresa> comboEmpresa = new ComboBox<>();
        comboEmpresa.getItems().setAll(empresaServicio.listarEmpresas());
        comboEmpresa.setValue(seleccionado.getEmpresa());

        TextField email = new TextField(seleccionado.getEmail());
        TextField telefono = new TextField(seleccionado.getTelefono());
        DatePicker fechaIngreso = new DatePicker(seleccionado.getFechaIngreso().toLocalDate());

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombre, 1, 0);

        grid.add(new Label("Apellidos:"), 0, 1);
        grid.add(apellidos, 1, 1);

        grid.add(new Label("Empresa:"), 0, 2);
        grid.add(comboEmpresa, 1, 2);

        grid.add(new Label("Email:"), 0, 3);
        grid.add(email, 1, 3);

        grid.add(new Label("Teléfono:"), 0, 4);
        grid.add(telefono, 1, 4);

        grid.add(new Label("Fecha ingreso:"), 0, 5);
        grid.add(fechaIngreso, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {

                seleccionado.setNombre(nombre.getText());
                seleccionado.setApellidos(apellidos.getText());
                seleccionado.setEmpresa(comboEmpresa.getValue());
                seleccionado.setEmail(email.getText());
                seleccionado.setTelefono(telefono.getText());
                seleccionado.setFechaIngreso(Date.valueOf(fechaIngreso.getValue()));

                tutorServicio.guardarTutor(seleccionado);
                return seleccionado;
            }
            return null;
        });

        dialog.showAndWait();
        tablaTutores.refresh();
    }

   
    @FXML
    private void eliminarTutor() {
        TutorEmpresa seleccionado = tablaTutores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un tutor", "Debes seleccionar un tutor para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Seguro que deseas eliminar al tutor?");
        confirmacion.setContentText(seleccionado.getNombre() + " " + seleccionado.getApellidos());

        Optional<ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            tutorServicio.eliminarTutor(seleccionado.getIdPersona());
            listaTutores.remove(seleccionado);
            tablaTutores.refresh();
        }
    }

   
    @FXML
    private void volverMenuAdmin() {
        stageManager.switchScene(FxmlView.MENUADMIN);
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarEstilos() {
        try {
            String cssPath = "/styles/GestionTutores.css";
            URL cssUrl = getClass().getResource(cssPath);
            if (cssUrl != null) {
                rootPane.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el CSS: " + e.getMessage());
        }
    }
}

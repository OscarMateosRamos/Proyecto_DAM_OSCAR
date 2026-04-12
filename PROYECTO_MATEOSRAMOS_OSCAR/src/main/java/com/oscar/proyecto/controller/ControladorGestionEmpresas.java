package com.oscar.proyecto.controller;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Empresa;
import com.oscar.proyecto.modelo.TutorEmpresa;
import com.oscar.proyecto.services.ServicioEmpresa;
import com.oscar.proyecto.services.ServicioTutorEmpresa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Component
public class ControladorGestionEmpresas {

    @Autowired
    private StageManager stageManager;

    @Autowired
    private ServicioEmpresa empresaServicio;

    @Autowired
    private ServicioTutorEmpresa tutorEmpresaServicio;

    @FXML
    private TableView<Empresa> tablaEmpresas;

    @FXML
    private TableColumn<Empresa, String> colNombre;

    @FXML
    private TableColumn<Empresa, LocalTime> colHorario;

    @FXML
    private TableColumn<Empresa, String> colTutores;

    private ObservableList<Empresa> listaEmpresas;

    @FXML
    private BorderPane rootPane;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarEmpresas();
        cargarEstilos();
    }

    private void configurarColumnas() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        colTutores.setCellValueFactory(data -> {
            try {
                List<TutorEmpresa> tutores = data.getValue().getTutores();
                if (tutores == null || tutores.isEmpty()) {
                    return new javafx.beans.property.SimpleStringProperty("Sin tutores");
                }
                String nombres = tutores.stream()
                    .map(t -> t.getNombre())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Sin tutores");
                return new javafx.beans.property.SimpleStringProperty(nombres);
            } catch (Exception e) {
                System.err.println("Error al cargar tutores: " + e.getMessage());
                return new javafx.beans.property.SimpleStringProperty("Sin tutores");
            }
        });
    }

    private void cargarEmpresas() {
        List<Empresa> empresas = empresaServicio.listarEmpresas();
        listaEmpresas = FXCollections.observableArrayList(empresas);
        tablaEmpresas.setItems(listaEmpresas);
    }

    @FXML
    private void añadirEmpresa() {

     
        Dialog<Empresa> dialog = new Dialog<>();
        dialog.setTitle("Añadir Empresa");
        dialog.setHeaderText("Introduce los datos de la nueva empresa:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre de la empresa");

        ComboBox<Integer> hora = new ComboBox<>();
        hora.getItems().addAll(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        hora.setPromptText("Hora");

        ComboBox<Integer> minuto = new ComboBox<>();
        minuto.getItems().addAll(0, 15, 30, 45);
        minuto.setPromptText("Minuto");

        HBox horarioBox = new HBox(10, hora, minuto);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombre, 1, 0);
        grid.add(new Label("Horario:"), 0, 1);
        grid.add(horarioBox, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                if (nombre.getText().isEmpty()) {
                    mostrarError("Nombre requerido", "La empresa debe tener un nombre.");
                    return null;
                }
                Empresa empresa = new Empresa();
                empresa.setNombre(nombre.getText());
                if (hora.getValue() != null && minuto.getValue() != null) {
                    empresa.setHorario(LocalTime.of(hora.getValue(), minuto.getValue()));
                }
                return empresa;
            }
            return null;
        });

        Optional<Empresa> result = dialog.showAndWait();

        result.ifPresent(empresa -> {
          
            Empresa empresaGuardada = empresaServicio.guardarEmpresa(empresa);

            Dialog<Void> dialogTutores = new Dialog<>();
            dialogTutores.setTitle("Asignar Tutores");
            dialogTutores.setHeaderText("Selecciona los tutores para \"" + empresaGuardada.getNombre() + "\":");

            ButtonType finalizarButtonType = new ButtonType("Finalizar", ButtonBar.ButtonData.OK_DONE);
            dialogTutores.getDialogPane().getButtonTypes().addAll(finalizarButtonType, ButtonType.CANCEL);

          
            List<TutorEmpresa> todosLosTutores = tutorEmpresaServicio.listarTutores();

            ListView<TutorEmpresa> listaTutores = new ListView<>();
            listaTutores.getItems().setAll(todosLosTutores);
            listaTutores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            listaTutores.setPrefHeight(300);
            listaTutores.setPrefWidth(400);

            VBox contenido = new VBox(10);
            contenido.setPadding(new Insets(10));
            contenido.getChildren().addAll(
                new Label("Mantén Ctrl para seleccionar varios:"),
                listaTutores
            );

            dialogTutores.getDialogPane().setContent(contenido);

            dialogTutores.setResultConverter(btn -> {
                if (btn == finalizarButtonType) {
                    List<TutorEmpresa> seleccionados = new ArrayList<>(
                        listaTutores.getSelectionModel().getSelectedItems()
                    );
                    for (TutorEmpresa tutor : seleccionados) {
                        tutor.setEmpresa(empresaGuardada);
                        tutorEmpresaServicio.guardarTutor(tutor);
                    }
                }
                return null;
            });

            dialogTutores.showAndWait();

          
            Empresa empresaActualizada = empresaServicio.buscarPorId(empresaGuardada.getIdEmpresa());
            listaEmpresas.add(empresaActualizada != null ? empresaActualizada : empresaGuardada);
            tablaEmpresas.refresh();
        });
    }

    @FXML
    private void editarEmpresa() {
        Empresa seleccionada = tablaEmpresas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarError("Selecciona una empresa", "Debes seleccionar una empresa para editarla.");
            return;
        }

        Dialog<Empresa> dialog = new Dialog<>();
        dialog.setTitle("Editar Empresa");
        dialog.setHeaderText("Modifica los datos de la empresa:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombre = new TextField(seleccionada.getNombre());

        ComboBox<Integer> hora = new ComboBox<>();
        hora.getItems().addAll(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        hora.setValue(seleccionada.getHorario().getHour());

        ComboBox<Integer> minuto = new ComboBox<>();
        minuto.getItems().addAll(0, 15, 30, 45);
        minuto.setValue(seleccionada.getHorario().getMinute());

        HBox horarioBox = new HBox(10, hora, minuto);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombre, 1, 0);
        grid.add(new Label("Horario:"), 0, 1);
        grid.add(horarioBox, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                seleccionada.setNombre(nombre.getText());
                seleccionada.setHorario(LocalTime.of(hora.getValue(), minuto.getValue()));
                empresaServicio.guardarEmpresa(seleccionada);
                return seleccionada;
            }
            return null;
        });

        dialog.showAndWait();
        tablaEmpresas.refresh();
    }

    @FXML
    private void eliminarEmpresa() {
        Empresa seleccionada = tablaEmpresas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarError("Selecciona una empresa", "Debes seleccionar una empresa para eliminarla.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Seguro que deseas eliminar la empresa?");
        confirmacion.setContentText(seleccionada.getNombre());

        Optional<ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            empresaServicio.eliminarEmpresa(seleccionada.getIdEmpresa());
            listaEmpresas.remove(seleccionada);
            tablaEmpresas.refresh();
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
            String cssPath = "/styles/GestionEmpresas.css";
            URL cssUrl = getClass().getResource(cssPath);
            if (cssUrl != null) {
                rootPane.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el CSS: " + e.getMessage());
        }
    }
}
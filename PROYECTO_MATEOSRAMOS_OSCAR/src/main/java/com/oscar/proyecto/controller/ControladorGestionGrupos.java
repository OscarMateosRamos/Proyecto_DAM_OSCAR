package com.oscar.proyecto.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Curso;
import com.oscar.proyecto.modelo.Estudiante;
import com.oscar.proyecto.modelo.Grupo;
import com.oscar.proyecto.services.ServicioCurso;
import com.oscar.proyecto.services.ServicioEstudiante;
import com.oscar.proyecto.services.ServicioGrupo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

@Component
public class ControladorGestionGrupos {

    @Autowired
    private StageManager stageManager;

    @Autowired
    private ServicioGrupo grupoServicio;

    @Autowired
    private ServicioCurso cursoServicio;

    @Autowired
    private ServicioEstudiante estudianteServicio;

    @FXML
    private TableView<Grupo> tablaGrupos;

    @FXML
    private TableColumn<Grupo, String> colCurso;

    @FXML
    private TableColumn<Grupo, String> colNombre;

    @FXML
    private TableColumn<Grupo, Integer> colEstudiantes;

    private ObservableList<Grupo> listaGrupos;

    @FXML
    private BorderPane rootPane;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarGrupos();
        cargarEstilos();
    }

    private void configurarColumnas() {
        colCurso.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCurso() != null ? data.getValue().getCurso().getNombre() : ""
            )
        );

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colEstudiantes.setCellValueFactory(data ->
            new javafx.beans.property.SimpleIntegerProperty(
                data.getValue().getEstudiantes() != null ? data.getValue().getEstudiantes().size() : 0
            ).asObject()
        );
    }

    private void cargarGrupos() {
        listaGrupos = FXCollections.observableArrayList(grupoServicio.listarGrupos());
        tablaGrupos.setItems(listaGrupos);
    }

    @FXML
    private void añadirGrupo() {

        // --- Diálogo 1: Datos básicos del grupo ---
        Dialog<Grupo> dialog = new Dialog<>();
        dialog.setTitle("Añadir Grupo");
        dialog.setHeaderText("Introduce los datos del nuevo grupo:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Curso> comboCurso = new ComboBox<>();
        comboCurso.getItems().setAll(cursoServicio.listarCursos());
        comboCurso.setPromptText("Selecciona curso");

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre del grupo");

        grid.add(new Label("Curso:"), 0, 0);
        grid.add(comboCurso, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nombre, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                if (comboCurso.getValue() == null) {
                    mostrarError("Curso requerido", "Debes seleccionar un curso.");
                    return null;
                }
                if (nombre.getText().isEmpty()) {
                    mostrarError("Nombre requerido", "El grupo debe tener un nombre.");
                    return null;
                }
                Grupo g = new Grupo();
                g.setCurso(comboCurso.getValue());
                g.setNombre(nombre.getText());
                return g;
            }
            return null;
        });

        Optional<Grupo> result = dialog.showAndWait();

        result.ifPresent(g -> {
            // Guardar el grupo primero para que tenga ID asignado
            Grupo grupoGuardado = grupoServicio.guardarGrupo(g);

            // --- Diálogo 2: Añadir estudiantes al grupo ---
            Dialog<Void> dialogEstudiantes = new Dialog<>();
            dialogEstudiantes.setTitle("Añadir Estudiantes");
            dialogEstudiantes.setHeaderText("Selecciona los estudiantes para el grupo \"" + grupoGuardado.getNombre() + "\":");

            ButtonType finalizarButtonType = new ButtonType("Finalizar", ButtonBar.ButtonData.OK_DONE);
            dialogEstudiantes.getDialogPane().getButtonTypes().addAll(finalizarButtonType, ButtonType.CANCEL);

            List<Estudiante> todosEstudiantes = estudianteServicio.listarEstudiantes();

            ListView<Estudiante> listaEstudiantes = new ListView<>();
            listaEstudiantes.getItems().setAll(todosEstudiantes);
            listaEstudiantes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            listaEstudiantes.setPrefHeight(300);
            listaEstudiantes.setPrefWidth(400);

            VBox contenido = new VBox(10);
            contenido.setPadding(new Insets(10));
            contenido.getChildren().addAll(new Label("Mantén Ctrl para seleccionar varios:"), listaEstudiantes);

            dialogEstudiantes.getDialogPane().setContent(contenido);

            dialogEstudiantes.setResultConverter(btn -> {
                if (btn == finalizarButtonType) {
                    List<Estudiante> seleccionados = new java.util.ArrayList<>(
                        listaEstudiantes.getSelectionModel().getSelectedItems()
                    );
                    for (Estudiante estudiante : seleccionados) {
                        estudiante.setGrupo(grupoGuardado);
                        estudianteServicio.guardarEstudiante(estudiante);
                    }
                }
                return null;
            });

            dialogEstudiantes.showAndWait();

          
            Grupo grupoActualizado = grupoServicio.buscarPorId(grupoGuardado.getIdGrupo());
            listaGrupos.add(grupoActualizado != null ? grupoActualizado : grupoGuardado);
            tablaGrupos.refresh();
        });
    }

    @FXML
    private void editarGrupo() {

        Grupo seleccionado = tablaGrupos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un grupo", "Debes seleccionar un grupo para editarlo.");
            return;
        }

        Dialog<Grupo> dialog = new Dialog<>();
        dialog.setTitle("Editar Grupo");
        dialog.setHeaderText("Modifica los datos del grupo:");

        ButtonType guardarButtonType = new ButtonType("Guardar cambios", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Curso> comboCurso = new ComboBox<>();
        comboCurso.getItems().setAll(cursoServicio.listarCursos());
        comboCurso.setValue(seleccionado.getCurso());

        TextField nombre = new TextField(seleccionado.getNombre());

        grid.add(new Label("Curso:"), 0, 0);
        grid.add(comboCurso, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nombre, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                if (comboCurso.getValue() == null) {
                    mostrarError("Curso requerido", "Debes seleccionar un curso.");
                    return null;
                }
                if (nombre.getText().isEmpty()) {
                    mostrarError("Nombre requerido", "El grupo debe tener un nombre.");
                    return null;
                }
                seleccionado.setCurso(comboCurso.getValue());
                seleccionado.setNombre(nombre.getText());
                return seleccionado;
            }
            return null;
        });

        Optional<Grupo> result = dialog.showAndWait();

        result.ifPresent(g -> {
            grupoServicio.guardarGrupo(g);
            tablaGrupos.refresh();
        });
    }

    @FXML
    private void eliminarGrupo() {

        Grupo seleccionado = tablaGrupos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un grupo", "Debes seleccionar un grupo para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Seguro que deseas eliminar el grupo?");
        confirmacion.setContentText(seleccionado.getNombre());

        Optional<ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            grupoServicio.eliminarGrupo(seleccionado.getIdGrupo());
            listaGrupos.remove(seleccionado);
            tablaGrupos.refresh();
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
            String cssPath = "/styles/GestionGrupos.css";
            URL cssUrl = getClass().getResource(cssPath);
            if (cssUrl != null) {
                rootPane.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el CSS: " + e.getMessage());
        }
    }
}
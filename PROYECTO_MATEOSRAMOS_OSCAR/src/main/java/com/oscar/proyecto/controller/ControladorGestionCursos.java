package com.oscar.proyecto.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Ciclo;
import com.oscar.proyecto.modelo.Curso;
import com.oscar.proyecto.modelo.Profesor;
import com.oscar.proyecto.services.ServicioCiclo;
import com.oscar.proyecto.services.ServicioCurso;
import com.oscar.proyecto.services.ServicioProfesor;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

@Component
public class ControladorGestionCursos {

    @Autowired
    private StageManager stageManager;

    @Autowired
    private ServicioCurso cursoServicio;

    @Autowired
    private ServicioCiclo cicloServicio;

    @Autowired
    private ServicioProfesor profesorServicio;

    @FXML
    private TableView<Curso> tablaCursos;

    @FXML
    private TableColumn<Curso, String> colCiclo;

    @FXML
    private TableColumn<Curso, String> colNombre;

    @FXML
    private TableColumn<Curso, String> colCodigo;

    @FXML
    private TableColumn<Curso, String> colAnio;

    @FXML
    private TableColumn<Curso, String> colProfesores;

    private ObservableList<Curso> listaCursos;

    @FXML
    private BorderPane rootPane;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarCursos();
        cargarEstilos();
    }

    private void configurarColumnas() {
        colCiclo.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCiclo() != null ? data.getValue().getCiclo().getNombre() : ""
            )
        );

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("añoAcademico"));

        colProfesores.setCellValueFactory(data ->
        new SimpleStringProperty(
            data.getValue().getProfesores() != null && !data.getValue().getProfesores().isEmpty()
                ? data.getValue().getProfesores().stream()
                    .filter(p -> p != null && p.getNombre() != null)
                    .map(Profesor::getNombre)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("")
                : "Sin profesores"
        )
    );

    }

    private void cargarCursos() {
        List<Curso> cursos = cursoServicio.listarCursos();
        listaCursos = FXCollections.observableArrayList(cursos);
        tablaCursos.setItems(listaCursos);
    }

    
    @FXML
    private void añadirCurso() {

        Dialog<Curso> dialog = new Dialog<>();
        dialog.setTitle("Añadir Curso");
        dialog.setHeaderText("Introduce los datos del nuevo curso:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Ciclo> comboCiclo = new ComboBox<>();
        comboCiclo.getItems().setAll(cicloServicio.listarCiclos());
        comboCiclo.setPromptText("Selecciona ciclo");

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre del curso");

        TextField codigo = new TextField();
        codigo.setPromptText("Código");

        TextField anio = new TextField();
        anio.setPromptText("Año académico");

        ListView<Profesor> listaProfesores = new ListView<>();
        listaProfesores.getItems().setAll(profesorServicio.listarProfesores());
        listaProfesores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listaProfesores.setPrefHeight(120);

        grid.add(new Label("Ciclo:"), 0, 0);
        grid.add(comboCiclo, 1, 0);

        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nombre, 1, 1);

        grid.add(new Label("Código:"), 0, 2);
        grid.add(codigo, 1, 2);

        grid.add(new Label("Año académico:"), 0, 3);
        grid.add(anio, 1, 3);

        grid.add(new Label("Profesores:"), 0, 4);
        grid.add(listaProfesores, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {

                Curso curso = new Curso();
                curso.setCiclo(comboCiclo.getValue());
                curso.setNombre(nombre.getText());
                curso.setCodigo(codigo.getText());
                curso.setAñoAcademico(anio.getText());
                curso.setProfesores(listaProfesores.getSelectionModel().getSelectedItems());

                return curso;
            }
            return null;
        });

        Optional<Curso> result = dialog.showAndWait();

        result.ifPresent(curso -> {
            cursoServicio.guardarCurso(curso);
            listaCursos.add(curso);
            tablaCursos.refresh();
        });
    }

    
    @FXML
    private void editarCurso() {
        Curso seleccionado = tablaCursos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un curso", "Debes seleccionar un curso para editarlo.");
            return;
        }

        Dialog<Curso> dialog = new Dialog<>();
        dialog.setTitle("Editar Curso");
        dialog.setHeaderText("Modifica los datos del curso:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Ciclo> comboCiclo = new ComboBox<>();
        comboCiclo.getItems().setAll(cicloServicio.listarCiclos());
        comboCiclo.setValue(seleccionado.getCiclo());

        TextField nombre = new TextField(seleccionado.getNombre());
        TextField codigo = new TextField(seleccionado.getCodigo());
        TextField anio = new TextField(seleccionado.getAñoAcademico());

        ListView<Profesor> listaProfesores = new ListView<>();
        listaProfesores.getItems().setAll(profesorServicio.listarProfesores());
        listaProfesores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listaProfesores.getSelectionModel().selectIndices(
            0,
            seleccionado.getProfesores().stream()
                .map(p -> listaProfesores.getItems().indexOf(p))
                .filter(i -> i >= 0)
                .mapToInt(i -> i)
                .toArray()
        );

        listaProfesores.setPrefHeight(120);

        grid.add(new Label("Ciclo:"), 0, 0);
        grid.add(comboCiclo, 1, 0);

        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nombre, 1, 1);

        grid.add(new Label("Código:"), 0, 2);
        grid.add(codigo, 1, 2);

        grid.add(new Label("Año académico:"), 0, 3);
        grid.add(anio, 1, 3);

        grid.add(new Label("Profesores:"), 0, 4);
        grid.add(listaProfesores, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {

                seleccionado.setCiclo(comboCiclo.getValue());
                seleccionado.setNombre(nombre.getText());
                seleccionado.setCodigo(codigo.getText());
                seleccionado.setAñoAcademico(anio.getText());
                seleccionado.setProfesores(listaProfesores.getSelectionModel().getSelectedItems());

                cursoServicio.guardarCurso(seleccionado);
                return seleccionado;
            }
            return null;
        });

        dialog.showAndWait();
        tablaCursos.refresh();
    }

   
    @FXML
    private void eliminarCurso() {
        Curso seleccionado = tablaCursos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un curso", "Debes seleccionar un curso para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Seguro que deseas eliminar el curso?");
        confirmacion.setContentText(seleccionado.getNombre());

        Optional<ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            cursoServicio.eliminarCurso(seleccionado.getIdCurso());
            listaCursos.remove(seleccionado);
            tablaCursos.refresh();
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
            String cssPath = "/styles/GestionCurso.css";
            URL cssUrl = getClass().getResource(cssPath);
            if (cssUrl != null) {
                rootPane.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el CSS: " + e.getMessage());
        }
    }
}

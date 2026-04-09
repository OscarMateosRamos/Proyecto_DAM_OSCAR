package com.oscar.proyecto.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Ciclo;
import com.oscar.proyecto.services.ServicioCiclo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

@Component
public class ControladorGestionCiclos {

    @Autowired
    private StageManager stageManager;

    @Autowired
    private ServicioCiclo cicloServicio;

    @FXML
    private TableView<Ciclo> tablaCiclos;

    @FXML
    private TableColumn<Ciclo, String> colNombre;

    @FXML
    private TableColumn<Ciclo, String> colDescripcion;

    @FXML
    private TableColumn<Ciclo, String> colCursos;

    private ObservableList<Ciclo> listaCiclos;

    @FXML
    private BorderPane rootPane;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarCiclos();
        cargarEstilos();
    }

    private void configurarColumnas() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        
        colCursos.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCursos() != null && !data.getValue().getCursos().isEmpty()
                    ? data.getValue().getCursos().stream()
                        .map(c -> c.getNombre()) 
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("")
                    : "Sin cursos"
            )
        );
    }

    private void cargarCiclos() {
        List<Ciclo> ciclos = cicloServicio.listarCiclos();
        listaCiclos = FXCollections.observableArrayList(ciclos);
        tablaCiclos.setItems(listaCiclos);
    }

  
    @FXML
    private void añadirCiclo() {

        Dialog<Ciclo> dialog = new Dialog<>();
        dialog.setTitle("Añadir Ciclo");
        dialog.setHeaderText("Introduce los datos del nuevo ciclo:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre del ciclo");

        TextArea descripcion = new TextArea();
        descripcion.setPromptText("Descripción del ciclo");
        descripcion.setPrefRowCount(3);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombre, 1, 0);

        grid.add(new Label("Descripción:"), 0, 1);
        grid.add(descripcion, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {

                Ciclo ciclo = new Ciclo();
                ciclo.setNombre(nombre.getText());
                ciclo.setDescripcion(descripcion.getText());

                return ciclo;
            }
            return null;
        });

        Optional<Ciclo> result = dialog.showAndWait();

        result.ifPresent(ciclo -> {
            cicloServicio.guardarCiclo(ciclo);
            listaCiclos.add(ciclo);
            tablaCiclos.refresh();
        });
    }

  
    @FXML
    private void editarCiclo() {
        Ciclo seleccionado = tablaCiclos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un ciclo", "Debes seleccionar un ciclo para editarlo.");
            return;
        }

        Dialog<Ciclo> dialog = new Dialog<>();
        dialog.setTitle("Editar Ciclo");
        dialog.setHeaderText("Modifica los datos del ciclo:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombre = new TextField(seleccionado.getNombre());

        TextArea descripcion = new TextArea(seleccionado.getDescripcion());
        descripcion.setPrefRowCount(3);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombre, 1, 0);

        grid.add(new Label("Descripción:"), 0, 1);
        grid.add(descripcion, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {

                seleccionado.setNombre(nombre.getText());
                seleccionado.setDescripcion(descripcion.getText());

                cicloServicio.guardarCiclo(seleccionado);
                return seleccionado;
            }
            return null;
        });

        dialog.showAndWait();
        tablaCiclos.refresh();
    }

    @FXML
    private void eliminarCiclo() {
        Ciclo seleccionado = tablaCiclos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un ciclo", "Debes seleccionar un ciclo para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Seguro que deseas eliminar el ciclo?");
        confirmacion.setContentText(seleccionado.getNombre());

        Optional<ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            cicloServicio.eliminarCiclo(seleccionado.getIdCiclo());
            listaCiclos.remove(seleccionado);
            tablaCiclos.refresh();
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
            String cssPath = "/styles/GestionCiclo.css";
            URL cssUrl = getClass().getResource(cssPath);
            if (cssUrl != null) {
                rootPane.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el CSS: " + e.getMessage());
        }
    }
}

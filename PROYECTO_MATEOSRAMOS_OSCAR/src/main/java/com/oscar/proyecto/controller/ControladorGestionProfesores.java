/**
 * Clase ControladorGestionProfesores.java
 *
 * @author Oscar
 */
package com.oscar.proyecto.controller;

import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Profesor;
import com.oscar.proyecto.services.ServicioProfesor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

@Component
public class ControladorGestionProfesores {

	@Autowired
	private StageManager stageManager;

	@Autowired
	private ServicioProfesor profesorServicio;

	@FXML
	private TableView<Profesor> tablaProfesores;

	@FXML
	private TableColumn<Profesor, String> colNombre;

	@FXML
	private TableColumn<Profesor, String> colApellidos;

	@FXML
	private TableColumn<Profesor, String> colCodigo;

	@FXML
	private TableColumn<Profesor, String> colDepartamento;

	@FXML
	private TableColumn<Profesor, String> colEmail;

	private ObservableList<Profesor> listaProfesores;

	@FXML
	private BorderPane rootPane;

	@FXML
	public void initialize() {
		configurarColumnas();
		cargarProfesores();

		cargarEstilos();
	}

	private void configurarColumnas() {
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colApellidos
				.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		colCodigo.setCellValueFactory(
				new PropertyValueFactory<>("codigoProfesor"));
		colDepartamento.setCellValueFactory(
				new PropertyValueFactory<>("departamento"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
	}

	private void cargarProfesores() {
		List<Profesor> profesores = profesorServicio.listarProfesores();
		listaProfesores = FXCollections.observableArrayList(profesores);
		tablaProfesores.setItems(listaProfesores);
	}

	@FXML
	private void añadirProfesor() {

		stageManager.switchScene(FxmlView.CREARPROFESOR);
	}

	@FXML
	private void editarProfesor() {
		Profesor seleccionado = tablaProfesores.getSelectionModel()
				.getSelectedItem();

		if (seleccionado == null) {
			mostrarError("Selecciona un profesor",
					"Debes seleccionar un profesor para editarlo.");
			return;
		}

		mostrarInfo("Funcionalidad pendiente",
				"Aquí se abrirá la ventana para editar al profesor seleccionado.");
	}

	@FXML
	private void eliminarProfesor() {
		Profesor seleccionado = tablaProfesores.getSelectionModel()
				.getSelectedItem();

		if (seleccionado == null) {
			mostrarError("Selecciona un profesor",
					"Debes seleccionar un profesor para eliminarlo.");
			return;
		}

		Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
				"¿Seguro que deseas eliminar al profesor seleccionado?",
				ButtonType.YES, ButtonType.NO);

		confirmacion.showAndWait();

		if (confirmacion.getResult() == ButtonType.YES) {
			profesorServicio.eliminarProfesor(seleccionado.getIdPersona());
			cargarProfesores();
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
		alert.show();
	}

	private void mostrarInfo(String titulo, String mensaje) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.show();
	}

	private void cargarEstilos() {
		try {
			String cssPath = "/styles/GestionProfesores.css";
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

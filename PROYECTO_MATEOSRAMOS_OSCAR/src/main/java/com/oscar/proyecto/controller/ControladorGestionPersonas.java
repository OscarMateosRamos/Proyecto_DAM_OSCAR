package com.oscar.proyecto.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Perfil;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.services.ServicioPersona;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

@Component
public class ControladorGestionPersonas {

	@Autowired
	private StageManager stageManager;

	@FXML
	private TableView<Persona> tablaPersonas;
	@FXML
	private TableColumn<Persona, String> colNombre;
	@FXML
	private TableColumn<Persona, String> colApellidos;
	@FXML
	private TableColumn<Persona, String> colUsuario;
	@FXML
	private TableColumn<Persona, Perfil> colPerfil;

	@FXML
	private Button btnAñadir;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnVolver;

	@Autowired
	private ServicioPersona personaServicio;

	private ObservableList<Persona> listaPersonas;

	@FXML
	private BorderPane rootPane;

	@FXML
	public void initialize() {
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colApellidos
				.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		colPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));

		cargarPersonas();
		
		
		cargarEstilos();
	}

	private void cargarPersonas() {
		List<Persona> personas = personaServicio.listarPersonas();
		listaPersonas = FXCollections.observableArrayList(personas);
		tablaPersonas.setItems(listaPersonas);
	}

	@FXML
	private void añadirPersona() {
		Dialog<Persona> dialog = new Dialog<>();
		dialog.setTitle("Añadir Nueva Persona");
		dialog.setHeaderText("Introduce los datos de la nueva persona:");

		ButtonType guardarButtonType = new ButtonType("Guardar",
				ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType,
				ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField nombre = new TextField();
		nombre.setPromptText("Nombre");
		TextField apellidos = new TextField();
		apellidos.setPromptText("Apellidos");
		TextField usuario = new TextField();
		usuario.setPromptText("Usuario");
		PasswordField contraseña = new PasswordField();
		contraseña.setPromptText("Contraseña");
		ComboBox<Perfil> perfil = new ComboBox<>();
		perfil.getItems().setAll(Perfil.values());
		perfil.setPromptText("Perfil");

		grid.add(new Label("Nombre:"), 0, 0);
		grid.add(nombre, 1, 0);
		grid.add(new Label("Apellidos:"), 0, 1);
		grid.add(apellidos, 1, 1);
		grid.add(new Label("Usuario:"), 0, 2);
		grid.add(usuario, 1, 2);
		grid.add(new Label("Contraseña:"), 0, 3);
		grid.add(contraseña, 1, 3);
		grid.add(new Label("Perfil:"), 0, 4);
		grid.add(perfil, 1, 4);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == guardarButtonType) {
				Persona nuevaPersona = new Persona(null, nombre.getText(),
						apellidos.getText(), usuario.getText(),
						contraseña.getText(), perfil.getValue());
				return nuevaPersona;
			}
			return null;
		});

		Optional<Persona> result = dialog.showAndWait();

		result.ifPresent(persona -> {
			personaServicio.guardarPersona(persona);
			listaPersonas.add(persona);
			tablaPersonas.setItems(listaPersonas);
		});
	}

	@FXML
	private void editarPersona() {
		Persona personaSeleccionada = tablaPersonas.getSelectionModel()
				.getSelectedItem();
		if (personaSeleccionada != null) {
			Dialog<Persona> dialog = new Dialog<>();
			dialog.setTitle("Editar Persona");
			dialog.setHeaderText("Edita los datos de la persona:");

			ButtonType guardarButtonType = new ButtonType("Guardar",
					ButtonBar.ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType,
					ButtonType.CANCEL);

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			TextField nombre = new TextField(personaSeleccionada.getNombre());
			TextField apellidos = new TextField(
					personaSeleccionada.getApellidos());
			TextField usuario = new TextField(personaSeleccionada.getUsuario());
			PasswordField contraseña = new PasswordField();
			contraseña.setPromptText(
					"Deja vacío para mantener la contraseña actual");
			ComboBox<Perfil> perfil = new ComboBox<>();
			perfil.getItems().setAll(Perfil.values());
			perfil.setValue(personaSeleccionada.getPerfil());

			grid.add(new Label("Nombre:"), 0, 0);
			grid.add(nombre, 1, 0);
			grid.add(new Label("Apellidos:"), 0, 1);
			grid.add(apellidos, 1, 1);
			grid.add(new Label("Usuario:"), 0, 2);
			grid.add(usuario, 1, 2);
			grid.add(new Label("Contraseña:"), 0, 3);
			grid.add(contraseña, 1, 3);
			grid.add(new Label("Perfil:"), 0, 4);
			grid.add(perfil, 1, 4);

			dialog.getDialogPane().setContent(grid);

			dialog.setResultConverter(dialogButton -> {
				if (dialogButton == guardarButtonType) {
					personaSeleccionada.setNombre(nombre.getText());
					personaSeleccionada.setApellidos(apellidos.getText());
					personaSeleccionada.setUsuario(usuario.getText());

					if (!contraseña.getText().isEmpty()) {
						personaSeleccionada.setContraseña(contraseña.getText()); // Se
																					// cifrará
																					// en
																					// el
																					// servicio
					}
					personaSeleccionada.setPerfil(perfil.getValue());
					personaServicio.guardarPersona(personaSeleccionada);
					return personaSeleccionada;
				}
				return null;
			});

			dialog.showAndWait();
			tablaPersonas.refresh();
		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Advertencia");
			alert.setHeaderText("No se ha seleccionado ninguna persona");
			alert.setContentText(
					"Por favor, selecciona una persona de la tabla.");
			alert.showAndWait();
		}
	}

	@FXML
	private void eliminarPersona() {
		Persona personaSeleccionada = tablaPersonas.getSelectionModel()
				.getSelectedItem();

		if (personaSeleccionada == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Advertencia");
			alert.setHeaderText("No se ha seleccionado ninguna persona");
			alert.setContentText(
					"Por favor, selecciona una persona de la tabla.");
			alert.showAndWait();
			return;
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmar eliminación");
		alert.setHeaderText(
				"¿Estás seguro de que quieres eliminar a esta persona?");
		alert.setContentText(personaSeleccionada.getNombre() + " "
				+ personaSeleccionada.getApellidos());

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				personaServicio
						.eliminarPersona(personaSeleccionada.getIdPersona());
				listaPersonas.remove(personaSeleccionada);
				tablaPersonas.refresh();

			} catch (IllegalStateException e) {
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("No se puede eliminar");
				error.setHeaderText("Eliminación bloqueada");
				error.setContentText(e.getMessage());
				error.showAndWait();

			} catch (Exception e) {
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("Error inesperado");
				error.setHeaderText("No se pudo eliminar la persona");
				error.setContentText("Detalles: " + e.getMessage());
				error.showAndWait();
			}
		}
	}
	
	
	 private void cargarEstilos() {
	        try {
	            String cssPath = "/styles/GestionPersona.css";
	            URL cssUrl = getClass().getResource(cssPath);
	            if (cssUrl != null) {
	                rootPane.getStylesheets().add(cssUrl.toExternalForm());
	            }
	        } catch (Exception e) {
	            System.err.println("Error al cargar el CSS: " + e.getMessage());
	        }
	    }
	

	@FXML
	private void volverMenuAdmin() {
		stageManager.switchScene(FxmlView.MENUADMIN);
	}
}

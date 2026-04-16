package com.oscar.proyecto.controller;

import javafx.geometry.Insets;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Curso;
import com.oscar.proyecto.modelo.Estudiante;
import com.oscar.proyecto.modelo.Grupo;
import com.oscar.proyecto.modelo.Perfil;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.services.ServicioCurso;
import com.oscar.proyecto.services.ServicioEstudiante;
import com.oscar.proyecto.services.ServicioGrupo;
import com.oscar.proyecto.services.ServicioPersona;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

@Component
public class ControladorGestionEstudiantes {

	@Autowired
	private StageManager stageManager;

	@Autowired
	private ServicioEstudiante estudianteServicio;

	@Autowired
	private ServicioPersona personaServicio;

	@Autowired
	private ServicioGrupo grupoServicio;

	@Autowired
	private ServicioCurso cursoServicio;

	@FXML
	private TableView<Estudiante> tablaEstudiantes;

	@FXML
	private TableColumn<Estudiante, String> colDni;

	@FXML
	private TableColumn<Estudiante, String> colEmail;

	@FXML
	private TableColumn<Estudiante, String> colGrupo;

	private ObservableList<Estudiante> listaEstudiantes;

	@FXML
	private BorderPane rootPane;

	@FXML
	public void initialize() {
		configurarColumnas();
		cargarEstudiantes();
		cargarEstilos();
	}

	private void configurarColumnas() {
		colDni.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getDni()));
		colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getEmail()));
		colGrupo.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getGrupo() != null
						? cellData.getValue().getGrupo().getNombre()
						: ""));
	}

	private void cargarEstudiantes() {
		listaEstudiantes = FXCollections
				.observableArrayList(estudianteServicio.listarEstudiantes());
		tablaEstudiantes.setItems(listaEstudiantes);
	}

	private boolean validarEmail(String email) {
		return email != null
				&& Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email);
	}

	private boolean validarTelefono(String telefono) {
		return telefono != null
				&& Pattern.matches("^[6789][0-9]{8}$", telefono);
	}

	private boolean validarDni(String dni) {
		return dni != null && Pattern.matches("^[0-9]{8}[A-Za-z]$", dni);
	}

	@FXML
	private void añadirEstudiante() {

		Dialog<Estudiante> dialog = new Dialog<>();
		dialog.setTitle("Añadir Estudiante");
		dialog.setHeaderText("Introduce los datos del nuevo estudiante:");

		ButtonType guardarButtonType = new ButtonType("Guardar",
				ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType,
				ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ComboBox<Persona> comboPersona = new ComboBox<>();
		comboPersona.getItems()
				.setAll(personaServicio.listarPersonas().stream()
						.filter(p -> p.getPerfil() == Perfil.ESTUDIANTE)
						.collect(Collectors.toList()));
		comboPersona.setPromptText("Selecciona persona");

		TextField dni = new TextField();
		dni.setPromptText("DNI");

		TextField email = new TextField();
		email.setPromptText("Email");

		TextField direccion = new TextField();
		direccion.setPromptText("Dirección");

		TextField telefono = new TextField();
		telefono.setPromptText("Teléfono");

		DatePicker fechaNac = new DatePicker();

		ComboBox<Curso> comboCurso = new ComboBox<>();
		comboCurso.getItems().setAll(cursoServicio.listarCursos());
		comboCurso.setPromptText("Selecciona curso");

		ComboBox<Grupo> comboGrupo = new ComboBox<>();
		comboGrupo.getItems().setAll(grupoServicio.listarGrupos());
		comboGrupo.setPromptText("Grupo");

		TextField horario = new TextField();
		horario.setPromptText("HH:mm (opcional)");

		grid.add(new Label("Persona:"), 0, 0);
		grid.add(comboPersona, 1, 0);
		grid.add(new Label("DNI:"), 0, 1);
		grid.add(dni, 1, 1);
		grid.add(new Label("Email:"), 0, 2);
		grid.add(email, 1, 2);
		grid.add(new Label("Dirección:"), 0, 3);
		grid.add(direccion, 1, 3);
		grid.add(new Label("Teléfono:"), 0, 4);
		grid.add(telefono, 1, 4);
		grid.add(new Label("Fecha nacimiento:"), 0, 5);
		grid.add(fechaNac, 1, 5);
		grid.add(new Label("Curso:"), 0, 6);
		grid.add(comboCurso, 1, 6);
		grid.add(new Label("Grupo:"), 0, 7);
		grid.add(comboGrupo, 1, 7);
		grid.add(new Label("Horario:"), 0, 8);
		grid.add(horario, 1, 8);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == guardarButtonType) {

				Persona p = comboPersona.getValue();

				if (p == null) {
					mostrarError("Persona requerida",
							"Debes seleccionar una persona.");
					return null;
				}
				if (!validarDni(dni.getText())) {
					mostrarError("DNI inválido",
							"El DNI debe tener 8 números y una letra.");
					return null;
				}
				if (!validarEmail(email.getText())) {
					mostrarError("Email inválido",
							"Introduce un email válido.");
					return null;
				}
				if (!validarTelefono(telefono.getText())) {
					mostrarError("Teléfono inválido",
							"El teléfono debe tener 9 dígitos.");
					return null;
				}
				if (fechaNac.getValue() == null) {
					mostrarError("Fecha requerida",
							"Debes seleccionar una fecha de nacimiento.");
					return null;
				}
				if (comboCurso.getValue() == null) {
					mostrarError("Curso requerido",
							"Debes seleccionar un curso.");
					return null;
				}

				Estudiante est = new Estudiante();
				est.setIdPersona(p.getIdPersona());
				est.setDni(dni.getText());
				est.setEmail(email.getText());
				est.setDireccion(direccion.getText());
				est.setTelefono(telefono.getText());
				est.setFechanac(java.sql.Date.valueOf(fechaNac.getValue()));
				est.setCurso(comboCurso.getValue().getNombre());
				est.setGrupo(comboGrupo.getValue());

				return est;
			}
			return null;
		});

		Optional<Estudiante> result = dialog.showAndWait();

		result.ifPresent(est -> {

			Estudiante completo = estudianteServicio.crearEstudiante(est);
			listaEstudiantes.add(completo);
			tablaEstudiantes.refresh();
		});
	}

	@FXML
	private void editarEstudiante() {

		Estudiante seleccionado = tablaEstudiantes.getSelectionModel()
				.getSelectedItem();

		if (seleccionado == null) {
			mostrarError("Selecciona un estudiante",
					"Debes seleccionar un estudiante para editarlo.");
			return;
		}

		Dialog<Estudiante> dialog = new Dialog<>();
		dialog.setTitle("Editar Estudiante");
		dialog.setHeaderText("Modifica los datos del estudiante:");

		ButtonType guardarButtonType = new ButtonType("Guardar cambios",
				ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType,
				ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		Label lblPersona = new Label(
				seleccionado.getNombre() + " " + seleccionado.getApellidos());

		TextField email = new TextField(seleccionado.getEmail());
		TextField direccion = new TextField(seleccionado.getDireccion());
		TextField telefono = new TextField(seleccionado.getTelefono());
		TextField horario = new TextField();

		grid.add(new Label("Persona:"), 0, 0);
		grid.add(lblPersona, 1, 0);
		grid.add(new Label("Email:"), 0, 1);
		grid.add(email, 1, 1);
		grid.add(new Label("Dirección:"), 0, 2);
		grid.add(direccion, 1, 2);
		grid.add(new Label("Teléfono:"), 0, 3);
		grid.add(telefono, 1, 3);
		grid.add(new Label("Horario:"), 0, 4);
		grid.add(horario, 1, 4);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == guardarButtonType) {

				if (!validarEmail(email.getText())) {
					mostrarError("Email inválido",
							"Introduce un email válido.");
					return null;
				}
				if (!validarTelefono(telefono.getText())) {
					mostrarError("Teléfono inválido",
							"El teléfono debe tener 9 dígitos.");
					return null;
				}

				seleccionado.setEmail(email.getText());
				seleccionado.setDireccion(direccion.getText());
				seleccionado.setTelefono(telefono.getText());

				return seleccionado;
			}
			return null;
		});

		Optional<Estudiante> result = dialog.showAndWait();

		result.ifPresent(est -> {

			estudianteServicio.guardarEstudiante(est);
			tablaEstudiantes.refresh();
		});
	}

	@FXML
	private void eliminarEstudiante() {

		Estudiante seleccionado = tablaEstudiantes.getSelectionModel()
				.getSelectedItem();

		if (seleccionado == null) {
			mostrarError("Selecciona un estudiante",
					"Debes seleccionar un estudiante para eliminarlo.");
			return;
		}

		Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
		confirmacion.setTitle("Confirmar eliminación");
		confirmacion
				.setHeaderText("¿Seguro que deseas eliminar al estudiante?");
		confirmacion.setContentText(
				seleccionado.getNombre() + " " + seleccionado.getApellidos());

		Optional<ButtonType> result = confirmacion.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				estudianteServicio
						.eliminarEstudiante(seleccionado.getIdPersona());
				listaEstudiantes.remove(seleccionado);
				tablaEstudiantes.refresh();
			} catch (Exception e) {
				mostrarError("Error inesperado", e.getMessage());
			}
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
			String cssPath = "/styles/GestionEstudiantes.css";
			URL cssUrl = getClass().getResource(cssPath);
			if (cssUrl != null) {
				rootPane.getStylesheets().add(cssUrl.toExternalForm());
			}
		} catch (Exception e) {
			System.err.println("Error al cargar el CSS: " + e.getMessage());
		}
	}
}
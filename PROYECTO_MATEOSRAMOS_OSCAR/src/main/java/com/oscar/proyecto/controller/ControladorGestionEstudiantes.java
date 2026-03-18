package com.oscar.proyecto.controller;

import javafx.geometry.Insets;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Estudiante;
import com.oscar.proyecto.modelo.Grupo;
import com.oscar.proyecto.modelo.Perfil;
import com.oscar.proyecto.modelo.Persona;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

@Component
public class ControladorGestionEstudiantes {

	@Autowired
	private StageManager stageManager;

	@Autowired
	private ServicioEstudiante estudianteServicio;

	@FXML
	private TableView<Estudiante> tablaEstudiantes;

	@FXML
	private TableColumn<Estudiante, String> colNombre;

	@FXML
	private TableColumn<Estudiante, String> colApellidos;

	@FXML
	private TableColumn<Estudiante, String> colDni;

	@FXML
	private TableColumn<Estudiante, String> colEmail;

	@FXML
	private TableColumn<Estudiante, String> colGrupo;

	private ObservableList<Estudiante> listaEstudiantes;

	@FXML
	private BorderPane rootPane;

	@Autowired
	ServicioGrupo grupoServicio;

	@Autowired
	ServicioPersona personaServicio;

	@FXML
	public void initialize() {
		configurarColumnas();
		cargarEstudiantes();
		cargarEstilos();
	}

	private void configurarColumnas() {
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colApellidos
				.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
	}

	private void cargarEstudiantes() {
		List<Estudiante> estudiantes = estudianteServicio.listarEstudiantes();
		listaEstudiantes = FXCollections.observableArrayList(estudiantes);
		tablaEstudiantes.setItems(listaEstudiantes);
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
		comboPersona.getItems().setAll(personaServicio.listarPersonas().stream()
				.filter(p -> p.getPerfil() == Perfil.ESTUDIANTE).toList());
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

		TextField curso = new TextField();
		curso.setPromptText("Curso");

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
		grid.add(curso, 1, 6);
		grid.add(new Label("Grupo:"), 0, 7);
		grid.add(comboGrupo, 1, 7);
		grid.add(new Label("Horario:"), 0, 8);
		grid.add(horario, 1, 8);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == guardarButtonType) {

				Estudiante est = new Estudiante();
				est.setIdPersona(comboPersona.getValue().getIdPersona());
				est.setDni(dni.getText());
				est.setEmail(email.getText());
				est.setDireccion(direccion.getText());
				est.setTelefono(telefono.getText());
				est.setFechanac(java.sql.Date.valueOf(fechaNac.getValue()));
				est.setCurso(curso.getText());
				est.setGrupo(comboGrupo.getValue());

				if (!horario.getText().isEmpty()) {
					est.setHorario(LocalDateTime.of(LocalDate.now(),
							LocalTime.parse(horario.getText())));
				}

				return est;
			}
			return null;
		});

		Optional<Estudiante> result = dialog.showAndWait();

		result.ifPresent(est -> {
			estudianteServicio.guardarEstudiante(est);
			listaEstudiantes.add(est);
			tablaEstudiantes.refresh();
		});
	}

	@FXML
	private void editarEstudiante() {

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

package com.oscar.proyecto.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Especialidad;
import com.oscar.proyecto.modelo.Perfil;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.modelo.Profesor;
import com.oscar.proyecto.services.ServicioPersona;
import com.oscar.proyecto.services.ServicioProfesor;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
import javafx.scene.layout.HBox;

@Component
public class ControladorGestionProfesores {

	@Autowired
	private StageManager stageManager;

	@Autowired
	private ServicioProfesor profesorServicio;

	@Autowired
	private ServicioPersona personaServicio;

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
	    colNombre.setCellValueFactory(cellData ->
	        new SimpleStringProperty(cellData.getValue().getNombre())
	    );
	    colApellidos.setCellValueFactory(cellData ->
	        new SimpleStringProperty(cellData.getValue().getApellidos())
	    );
	    colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoProfesor"));
	    colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
	    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
	}

	private void cargarProfesores() {
		List<Profesor> profesores = profesorServicio.listarProfesores();
		listaProfesores = FXCollections.observableArrayList(profesores);
		tablaProfesores.setItems(listaProfesores);
	}

	@FXML
	private void añadirProfesor() {

		Dialog<Profesor> dialog = new Dialog<>();
		dialog.setTitle("Añadir Profesor");
		dialog.setHeaderText("Introduce los datos del nuevo profesor:");

		ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ComboBox<Persona> comboPersona = new ComboBox<>();
		comboPersona.getItems().setAll(
				personaServicio.listarPersonas().stream().filter(p -> p.getPerfil() == Perfil.PROFESOR).toList());

		comboPersona.setPromptText("Selecciona persona");

		TextField codigo = new TextField();
		codigo.setPromptText("Código profesor");

		TextField departamento = new TextField();
		departamento.setPromptText("Departamento");

		TextField email = new TextField();
		email.setPromptText("Email");

		ComboBox<Especialidad> especialidad = new ComboBox<>();
		especialidad.getItems().setAll(Especialidad.values());
		especialidad.setPromptText("Especialidad");

		TextField aula = new TextField();
		aula.setPromptText("Aula asignada");

		CheckBox coordinador = new CheckBox("Es coordinador");

		DatePicker fechaIngreso = new DatePicker();

		ComboBox<Integer> hora = new ComboBox<>();
		hora.getItems().addAll(IntStream.range(8, 21).boxed().toList());
		hora.setPromptText("Hora");

		ComboBox<Integer> minuto = new ComboBox<>();
		minuto.getItems().addAll(0, 15, 30, 45);
		minuto.setPromptText("Minuto");

		HBox horarioBox = new HBox(10, hora, minuto);

		grid.add(new Label("Persona:"), 0, 0);
		grid.add(comboPersona, 1, 0);
		grid.add(new Label("Código:"), 0, 1);
		grid.add(codigo, 1, 1);
		grid.add(new Label("Departamento:"), 0, 2);
		grid.add(departamento, 1, 2);
		grid.add(new Label("Email:"), 0, 3);
		grid.add(email, 1, 3);
		grid.add(new Label("Especialidad:"), 0, 4);
		grid.add(especialidad, 1, 4);
		grid.add(new Label("Aula:"), 0, 5);
		grid.add(aula, 1, 5);
		grid.add(new Label("Fecha ingreso:"), 0, 6);
		grid.add(fechaIngreso, 1, 6);
		grid.add(new Label("Horario tutoría:"), 0, 7);
		grid.add(horarioBox, 1, 7);
		grid.add(coordinador, 1, 8);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == guardarButtonType) {

				Persona p = comboPersona.getValue();

				Profesor profesor = new Profesor();

				profesor.setIdPersona(p.getIdPersona());

				profesor.setCodigoProfesor(codigo.getText());
				profesor.setDepartamento(departamento.getText());
				profesor.setEmail(email.getText());
				profesor.setEspecialidad(especialidad.getValue());
				profesor.setAulaAsignada(aula.getText());
				profesor.setEsCoordinador(coordinador.isSelected());
				profesor.setFechaIngreso(fechaIngreso.getValue());

				LocalTime horaTutoria = LocalTime.of(hora.getValue(), minuto.getValue());
				profesor.setHorarioTutoria(LocalDateTime.of(LocalDate.now(), horaTutoria));

				return profesor;
			}
			return null;
		});

		Optional<Profesor> result = dialog.showAndWait();

		result.ifPresent(profesor -> {
			profesorServicio.guardarProfesor(profesor);
			listaProfesores.add(profesor);
			tablaProfesores.refresh();
		});
	}

	@FXML
	private void editarProfesor() {
		Profesor seleccionado = tablaProfesores.getSelectionModel().getSelectedItem();

		if (seleccionado == null) {
			mostrarError("Selecciona un profesor", "Debes seleccionar un profesor para editarlo.");
			return;
		}

		Dialog<Profesor> dialog = new Dialog<>();
		dialog.setTitle("Editar Profesor");
		dialog.setHeaderText("Modifica los datos del profesor:");

		ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField nombre = new TextField(seleccionado.getNombre());
		TextField apellidos = new TextField(seleccionado.getApellidos());

		TextField codigo = new TextField(seleccionado.getCodigoProfesor());
		TextField departamento = new TextField(seleccionado.getDepartamento());
		TextField email = new TextField(seleccionado.getEmail());
		ComboBox<Especialidad> especialidad = new ComboBox<>();
		especialidad.getItems().setAll(Especialidad.values());
		especialidad.setValue(seleccionado.getEspecialidad());
		TextField aula = new TextField(seleccionado.getAulaAsignada());
		CheckBox coordinador = new CheckBox("Es coordinador");
		coordinador.setSelected(seleccionado.isEsCoordinador());
		DatePicker fechaIngreso = new DatePicker(seleccionado.getFechaIngreso());
		TextField horario = new TextField(
				seleccionado.getHorarioTutoria() != null ? seleccionado.getHorarioTutoria().toLocalTime().toString()
						: "");

		grid.add(new Label("Nombre:"), 0, 0);
		grid.add(nombre, 1, 0);

		grid.add(new Label("Apellidos:"), 0, 1);
		grid.add(apellidos, 1, 1);

		grid.add(new Label("Código:"), 0, 2);
		grid.add(codigo, 1, 2);
		grid.add(new Label("Departamento:"), 0, 3);
		grid.add(departamento, 1, 3);
		grid.add(new Label("Email:"), 0, 4);
		grid.add(email, 1, 4);
		grid.add(new Label("Especialidad:"), 0, 5);
		grid.add(especialidad, 1, 5);
		grid.add(new Label("Aula:"), 0, 6);
		grid.add(aula, 1, 6);
		grid.add(new Label("Fecha ingreso:"), 0, 7);
		grid.add(fechaIngreso, 1, 7);
		grid.add(new Label("Horario tutoria:"), 0, 8);
		grid.add(horario, 1, 8);
		grid.add(coordinador, 1, 9);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == guardarButtonType) {

				seleccionado.setNombre(nombre.getText());
				seleccionado.setApellidos(apellidos.getText());

				seleccionado.setCodigoProfesor(codigo.getText());
				seleccionado.setDepartamento(departamento.getText());
				seleccionado.setEmail(email.getText());
				seleccionado.setEspecialidad(especialidad.getValue());
				seleccionado.setAulaAsignada(aula.getText());
				seleccionado.setEsCoordinador(coordinador.isSelected());
				seleccionado.setFechaIngreso(fechaIngreso.getValue());
				seleccionado.setHorarioTutoria(LocalDateTime.of(LocalDate.now(), LocalTime.parse(horario.getText())));

				profesorServicio.guardarProfesor(seleccionado);
				return seleccionado;
			}
			return null;
		});

		dialog.showAndWait();
		tablaProfesores.refresh();
	}

	@FXML
	private void eliminarProfesor() {
		Profesor seleccionado = tablaProfesores.getSelectionModel().getSelectedItem();

		if (seleccionado == null) {
			mostrarError("Selecciona un profesor", "Debes seleccionar un profesor para eliminarlo.");
			return;
		}

		Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
		confirmacion.setTitle("Confirmar eliminación");
		confirmacion.setHeaderText("¿Seguro que deseas eliminar al profesor?");
		confirmacion.setContentText(seleccionado.getNombre() + " " + seleccionado.getApellidos());

		Optional<ButtonType> result = confirmacion.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				profesorServicio.eliminarProfesor(seleccionado.getIdPersona());
				listaProfesores.remove(seleccionado);
				tablaProfesores.refresh();

			} catch (IllegalStateException e) {
				mostrarError("No se puede eliminar", e.getMessage());

			} catch (Exception e) {
				mostrarError("Error inesperado", "No se pudo eliminar el profesor.\nDetalles: " + e.getMessage());
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
			String cssPath = "/styles/GestionProfesores.css";
			URL cssUrl = getClass().getResource(cssPath);
			if (cssUrl != null) {
				rootPane.getStylesheets().add(cssUrl.toExternalForm());
			}
		} catch (Exception e) {
			System.err.println("Error al cargar el CSS: " + e.getMessage());
		}
	}
}

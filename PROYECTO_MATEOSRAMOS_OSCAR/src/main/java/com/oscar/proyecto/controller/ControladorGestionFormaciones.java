package com.oscar.proyecto.controller;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Empresa;
import com.oscar.proyecto.modelo.Estudiante;
import com.oscar.proyecto.modelo.FormacionEmpresa;
import com.oscar.proyecto.modelo.Periodo;
import com.oscar.proyecto.modelo.Profesor;
import com.oscar.proyecto.modelo.TutorEmpresa;
import com.oscar.proyecto.services.ServicioEmpresa;
import com.oscar.proyecto.services.ServicioEstudiante;
import com.oscar.proyecto.services.ServicioFormacionEmpresa;
import com.oscar.proyecto.services.ServicioProfesor;
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
public class ControladorGestionFormaciones {

	@Autowired
	private StageManager stageManager;
	@Autowired
	private ServicioFormacionEmpresa servicioFormacion;
	@Autowired
	private ServicioEstudiante servicioEstudiante;
	@Autowired
	private ServicioEmpresa servicioEmpresa;
	@Autowired
	private ServicioTutorEmpresa servicioTutor;
	@Autowired
	private ServicioProfesor servicioProfesor;

	@FXML
	private TextField txtBuscar;
	@FXML
	private ComboBox<Periodo> cboPeriodo;

	@FXML
	private TableView<FormacionEmpresa> tablaFormaciones;
	@FXML
	private TableColumn<FormacionEmpresa, Long> colId;
	@FXML
	private TableColumn<FormacionEmpresa, String> colEstudiante;
	@FXML
	private TableColumn<FormacionEmpresa, String> colEmpresa;
	@FXML
	private TableColumn<FormacionEmpresa, String> colTutorEmpresa;
	@FXML
	private TableColumn<FormacionEmpresa, String> colProfesorCoord;
	@FXML
	private TableColumn<FormacionEmpresa, Date> colFechaInicio;
	@FXML
	private TableColumn<FormacionEmpresa, Date> colFechaFin;
	@FXML
	private TableColumn<FormacionEmpresa, Periodo> colPeriodo;

	@FXML
	private BorderPane rootPane;

	private ObservableList<FormacionEmpresa> listaFormaciones;

	@FXML
	public void initialize() {
		configurarColumnas();
		cargarPeriodos();
		cargarFormaciones();
		cargarEstilos();

		txtBuscar.textProperty().addListener((obs, oldV, newV) -> filtrar());
		cboPeriodo.valueProperty().addListener((obs, oldV, newV) -> filtrar());
	}

	private void configurarColumnas() {
		colId.setCellValueFactory(new PropertyValueFactory<>("idFormacion"));

		colEstudiante.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getEstudiante() != null ? data.getValue().getEstudiante().getNombre() : ""));

		colEmpresa.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getEmpresa() != null ? data.getValue().getEmpresa().getNombre() : ""));

		colTutorEmpresa.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getTutorEmpresa() != null ? data.getValue().getTutorEmpresa().getNombre() : ""));

		colProfesorCoord.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getProfesorCoordinador() != null ? data.getValue().getProfesorCoordinador().getNombre()
						: ""));

		colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
		colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
		colPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
	}

	private void cargarPeriodos() {
		cboPeriodo.setItems(FXCollections.observableArrayList(Periodo.values()));
	}

	private void cargarFormaciones() {
		List<FormacionEmpresa> lista = servicioFormacion.listarFormaciones();
		listaFormaciones = FXCollections.observableArrayList(lista);
		tablaFormaciones.setItems(listaFormaciones);
	}

	private void filtrar() {
		String texto = txtBuscar.getText().toLowerCase();
		Periodo periodoSel = cboPeriodo.getValue();

		List<FormacionEmpresa> filtrado = listaFormaciones.stream()
				.filter(f -> (texto.isEmpty() || f.getEstudiante().getNombre().toLowerCase().contains(texto)
						|| f.getEmpresa().getNombre().toLowerCase().contains(texto))
						&& (periodoSel == null || f.getPeriodo() == periodoSel))
				.collect(Collectors.toList());

		tablaFormaciones.setItems(FXCollections.observableArrayList(filtrado));
	}

	@FXML
	private void añadirFormacion() {
		Dialog<FormacionEmpresa> dialog = crearDialogoFormacion(null);
		Optional<FormacionEmpresa> result = dialog.showAndWait();

		result.ifPresent(f -> {
			servicioFormacion.guardar(f);
			listaFormaciones.add(f);
			tablaFormaciones.refresh();
		});
	}

	@FXML
	private void editarFormacion() {
		FormacionEmpresa seleccion = tablaFormaciones.getSelectionModel().getSelectedItem();

		if (seleccion == null) {
			mostrarError("Selecciona una formación", "Debes seleccionar una formación para editarla.");
			return;
		}

		Dialog<FormacionEmpresa> dialog = crearDialogoFormacion(seleccion);
		dialog.showAndWait();
		tablaFormaciones.refresh();
	}

	@FXML
	private void eliminarFormacion() {
		FormacionEmpresa seleccion = tablaFormaciones.getSelectionModel().getSelectedItem();

		if (seleccion == null) {
			mostrarError("Selecciona una formación", "Debes seleccionar una formación para eliminarla.");
			return;
		}

		Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
		confirm.setTitle("Confirmar eliminación");
		confirm.setHeaderText("¿Seguro que deseas eliminar esta formación?");
		confirm.setContentText("ID: " + seleccion.getIdFormacion());

		Optional<ButtonType> result = confirm.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			servicioFormacion.eliminar(seleccion.getIdFormacion());
			listaFormaciones.remove(seleccion);
			tablaFormaciones.refresh();
		}
	}

	private Dialog<FormacionEmpresa> crearDialogoFormacion(FormacionEmpresa original) {

		boolean editando = original != null;

		Dialog<FormacionEmpresa> dialog = new Dialog<>();
		dialog.setTitle(editando ? "Editar Formación" : "Añadir Formación");
		dialog.setHeaderText(editando ? "Modifica los datos" : "Introduce los datos");

		ButtonType guardarBtn = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(guardarBtn, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));
		
		
		List<Estudiante> estudiantes = servicioEstudiante.listarEstudiantes();
		List<Empresa> empresas = servicioEmpresa.listarEmpresas();
		List<TutorEmpresa> tutores = servicioTutor.listarTutores();
		List<Profesor> profesores = servicioProfesor.listarProfesores();

		System.out.println("Estudiantes cargados: " + estudiantes.size());
		System.out.println("Empresas cargadas: " + empresas.size());
		System.out.println("Tutores cargados: " + tutores.size());
		System.out.println("Profesores cargados: " + profesores.size());


		ComboBox<Estudiante> cboEstudiante = new ComboBox<>();
		cboEstudiante.setItems(FXCollections.observableArrayList(servicioEstudiante.listarEstudiantes()));

		ComboBox<Empresa> cboEmpresa = new ComboBox<>();
		cboEmpresa.setItems(FXCollections.observableArrayList(servicioEmpresa.listarEmpresas()));

		ComboBox<TutorEmpresa> cboTutor = new ComboBox<>();
		cboTutor.setItems(FXCollections.observableArrayList(servicioTutor.listarTutores()));

		ComboBox<Profesor> cboProfesor = new ComboBox<>();
		cboProfesor.setItems(FXCollections.observableArrayList(servicioProfesor.listarProfesores()));

		DatePicker fechaInicio = new DatePicker();
		DatePicker fechaFin = new DatePicker();

		ComboBox<Periodo> cboPeriodoDialog = new ComboBox<>();
		cboPeriodoDialog.setItems(FXCollections.observableArrayList(Periodo.values()));

		if (editando) {
			cboEstudiante.setValue(original.getEstudiante());
			cboEmpresa.setValue(original.getEmpresa());
			cboTutor.setValue(original.getTutorEmpresa());
			cboProfesor.setValue(original.getProfesorCoordinador());
			fechaInicio.setValue(original.getFechaInicio().toLocalDate());
			fechaFin.setValue(original.getFechaFin().toLocalDate());
			cboPeriodoDialog.setValue(original.getPeriodo());
		}

		grid.add(new Label("Estudiante:"), 0, 0);
		grid.add(cboEstudiante, 1, 0);

		grid.add(new Label("Empresa:"), 0, 1);
		grid.add(cboEmpresa, 1, 1);

		grid.add(new Label("Tutor empresa:"), 0, 2);
		grid.add(cboTutor, 1, 2);

		grid.add(new Label("Profesor coord.:"), 0, 3);
		grid.add(cboProfesor, 1, 3);

		grid.add(new Label("Fecha inicio:"), 0, 4);
		grid.add(fechaInicio, 1, 4);

		grid.add(new Label("Fecha fin:"), 0, 5);
		grid.add(fechaFin, 1, 5);

		grid.add(new Label("Periodo:"), 0, 6);
		grid.add(cboPeriodoDialog, 1, 6);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(btn -> {
			if (btn == guardarBtn) {

				FormacionEmpresa f = editando ? original : new FormacionEmpresa();

				f.setEstudiante(cboEstudiante.getValue());
				f.setEmpresa(cboEmpresa.getValue());
				f.setTutorEmpresa(cboTutor.getValue());
				f.setProfesorCoordinador(cboProfesor.getValue());
				f.setFechaInicio(Date.valueOf(fechaInicio.getValue()));
				f.setFechaFin(Date.valueOf(fechaFin.getValue()));
				f.setPeriodo(cboPeriodoDialog.getValue());

				servicioFormacion.guardar(f);
				return f;
			}
			return null;
		});

		return dialog;
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
			String cssPath = "/styles/GestionFormaciones.css";
			URL cssUrl = getClass().getResource(cssPath);
			if (cssUrl != null) {
				rootPane.getStylesheets().add(cssUrl.toExternalForm());
			}
		} catch (Exception e) {
			System.err.println("Error al cargar el CSS: " + e.getMessage());
		}
	}
}

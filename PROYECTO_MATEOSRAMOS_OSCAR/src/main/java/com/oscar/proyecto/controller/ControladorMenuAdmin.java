package com.oscar.proyecto.controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.services.ServicioSesion;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

@Component
public class ControladorMenuAdmin {

	@Autowired
	private StageManager stageManager;

	@Autowired
	private ServicioSesion sesionServicio;

	@FXML
	private BorderPane rootPane;

	@FXML
	public void initialize() {
		cargarEstilos();
	}

	@FXML
	private void abrirGestionProfesores() {
		stageManager.switchScene(FxmlView.GESTIONPROFESORES);
	}

	@FXML
	private void abrirGestionCursos() {
		stageManager.switchScene(FxmlView.GESTIONCURSOS);
	}

	@FXML
	private void abrirGestionEstudiantes() {
		stageManager.switchScene(FxmlView.GESTIONESTUDIANTES);
	}

	@FXML
	private void abrirGestionEmpresas() {
		stageManager.switchScene(FxmlView.GESTIONEMPRESAS);

	}

	@FXML
	private void abrirGestionTutores() {

		stageManager.switchScene(FxmlView.GESTIONTUTORESEMPRESA);

	}

	@FXML
	private void abrirGestionGrupos() {
		stageManager.switchScene(FxmlView.GESTIONGRUPOS);
	}

	@FXML
	private void abrirGestionCiclos() {
		stageManager.switchScene(FxmlView.GESTIONCICLOS);
	}

	@FXML
	private void abrirGestionPersonas() {
		stageManager.switchScene(FxmlView.GESTIONPERSONAS);

	}

	private void cargarEstilos() {
		try {
			String cssPath = "/styles/MenuAdmin.css";
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

	@FXML
	private void cerrarSesion() {
		sesionServicio.limpiarSesion();
		stageManager.switchScene(FxmlView.LOGIN);
	}
}

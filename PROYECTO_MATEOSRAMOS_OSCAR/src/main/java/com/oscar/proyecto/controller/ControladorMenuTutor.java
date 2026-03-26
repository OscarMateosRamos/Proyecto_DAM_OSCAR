/**
*Clase ControladorMenuProfesor.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.services.ServicioSesion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
@Controller
public class ControladorMenuTutor {

	@Autowired
	private StageManager stageManager;

	@Autowired
	private ServicioSesion sesionServicio;

	@FXML
	private BorderPane rootPane;
	
	@FXML
	private Label lblBienvenida; 
	
	
	@FXML
	public void initialize() {
		cargarEstilos();
		cargarNombreProfesor();
	}
	
	private void cargarNombreProfesor() {
	    Persona usuario = sesionServicio.getUsuarioActual();

	    if (usuario != null) {
	        lblBienvenida.setText("BIENVENIDO " + usuario.getNombre()+" "+usuario.getApellidos());
	    } else {
	        lblBienvenida.setText("BIENVENIDO");
	    }
	}

	@FXML
	private void calificarestudiante() {
		
	}

	@FXML
	private void registrarfaltas() {

	}

	@FXML
	private void verdatosestudiante() {
		
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

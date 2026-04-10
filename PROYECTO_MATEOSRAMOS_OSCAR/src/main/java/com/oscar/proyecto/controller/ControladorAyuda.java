package com.oscar.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ControladorAyuda implements Initializable {

	@FXML
	private WebView webView;

	private static String archivoAyuda = "/help/ayuda_default.html";

	public static void setArchivoAyuda(String ruta) {
		archivoAyuda = ruta;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			WebEngine engine = webView.getEngine();
			URL helpUrl = getClass().getResource(archivoAyuda);

			if (helpUrl != null) {
				engine.load(helpUrl.toExternalForm());
			} else {
				engine.loadContent("<h1>Error</h1><p>No se encontró la ayuda solicitada.</p>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			webView.getEngine().loadContent("<h1>Error</h1><p>No se pudo cargar la ayuda.</p>");
		}
	}

	@FXML
	private void cerrarAyuda() {
		webView.getScene().getWindow().hide();
	}

}

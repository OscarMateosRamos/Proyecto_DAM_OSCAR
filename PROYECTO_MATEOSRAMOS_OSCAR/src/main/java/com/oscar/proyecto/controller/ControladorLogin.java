package com.oscar.proyecto.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorLogin implements Initializable {

    @FXML
    private VBox rootPane;

    @FXML
    private TextField usuarioField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox showPasswordCheck;

    @FXML
    private Button iniciarSesionButton;

    @FXML
    private Button recuperarPasswordButton;

    @FXML
    private Label mensajeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        rootPane = (VBox) usuarioField.getParent().getParent().getParent(); // Solución temporal si no puedes editar el FXML
        cargarEstilos();
    }

    private void cargarEstilos() {
        try {
            String cssPath = "/styles/login.css";
            URL cssUrl = getClass().getResource(cssPath);
            if (cssUrl != null) {
                System.out.println("CSS encontrado en: " + cssUrl.toExternalForm());
                rootPane.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("CSS NO encontrado en: " + cssPath);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el CSS: " + e.getMessage());
        }
    }

    @FXML
    private void handleIniciarSesion() {
        String usuario = usuarioField.getText();
        String contrasena = passwordField.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mensajeLabel.setText("Por favor, completa todos los campos.");
        } else {
            mensajeLabel.setText("");
            System.out.println("Iniciar sesión con usuario: " + usuario);
            
        }
    }

    @FXML
    private void handleRecuperarPassword() {
        System.out.println("Recuperar contraseña");
        
    }

    @FXML
    private void mostrarAyuda() {
        System.out.println("Mostrar ayuda");
        
    }
}

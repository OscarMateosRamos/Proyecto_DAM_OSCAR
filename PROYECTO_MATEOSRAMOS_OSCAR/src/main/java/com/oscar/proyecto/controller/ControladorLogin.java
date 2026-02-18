package com.oscar.proyecto.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.repositorios.PersonaRepository;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

@Component
public class ControladorLogin implements Initializable {

    @FXML private VBox rootPane;
    @FXML private TextField usuarioField;

    @FXML private PasswordField passwordField;
    @FXML private TextField passwordVisibleField;

    @FXML private ImageView togglePasswordIcon;

    @FXML private Button iniciarSesionButton;
    @FXML private Button recuperarPasswordButton;

    @FXML private Label mensajeLabel;
    
    @FXML
    private ImageView Logo;
    
    @Autowired
    PersonaRepository personaRepository;
    
    @Autowired
    StageManager stagemanager;
    private boolean passwordVisible = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarEstilos();
        cargarImagen();
    }

    private void cargarImagen() {
    	 try (InputStream inputStream = getClass().getResourceAsStream("/images/LogoColegio.png")) {
             if (inputStream != null) {
                 Logo.setImage(new Image(inputStream));
             } else {
                 System.err.println("Imagen NO encontrada en: /img/circo.png");
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    private void cargarEstilos() {
        try {
            String cssPath = "/styles/login.css";
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
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
           
            passwordVisibleField.setText(passwordField.getText());
            passwordVisibleField.setVisible(true);
            passwordVisibleField.setManaged(true);

            passwordField.setVisible(false);
            passwordField.setManaged(false);

            togglePasswordIcon.setImage(
                new Image(getClass().getResourceAsStream("/icons/OjoCerrado.png"))
            );

        } else {
           
            passwordField.setText(passwordVisibleField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);

            passwordVisibleField.setVisible(false);
            passwordVisibleField.setManaged(false);

            togglePasswordIcon.setImage(
                new Image(getClass().getResourceAsStream("/icons/OjoAbierto.png"))
            );
        }
    }

    @FXML
    private void handleIniciarSesion(javafx.event.ActionEvent event) {

        String usuario = usuarioField.getText();

        
        String password = passwordField.isVisible()
                ? passwordField.getText()
                : passwordVisibleField.getText();

      
        if (usuario.isEmpty() || password.isEmpty()) {
            mensajeLabel.setText("Usuario y contraseña son obligatorios.");
            mensajeLabel.setStyle("-fx-text-fill: red;");
            return;
        }

       
        Persona persona = personaRepository.findByUsuario(usuario).orElse(null);

        if (persona == null) {
            mensajeLabel.setText("Usuario no encontrado.");
            mensajeLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        
        if (!persona.getContraseña().equals(password)) {
            mensajeLabel.setText("Contraseña incorrecta.");
            mensajeLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        
        mensajeLabel.setText("Inicio de sesión correcto.");
        mensajeLabel.setStyle("-fx-text-fill: green;");

        
     

       
        switch (persona.getPerfil()) {
        case ADMIN:
           stagemanager.switchScene(null);
            break;

        case PROFESOR:
           
            break;

        case  TUTOR:
         
            break;
            
            
        case  ESTUDIANTE:
            
            break;

        default:
            mensajeLabel.setText("Perfil no reconocido.");
            mensajeLabel.setStyle("-fx-text-fill: red;");
    }

    }


    @FXML
    private void handleRecuperarPassword() {
        System.out.println("Recuperar contraseña");
    }

    @FXML
    private void mostrarAyuda() {
        Alert ayuda = new Alert(Alert.AlertType.INFORMATION);
        ayuda.setTitle("Ayuda - Inicio de Sesión");
        ayuda.setHeaderText("¿Cómo iniciar sesión?");
        ayuda.setContentText(
                "1. Introduce tu usuario.\n" +
                "2. Introduce tu contraseña.\n" +
                "3. Pulsa el icono del ojo para ver la contraseña.\n" +
                "4. Si no recuerdas tu contraseña, pulsa 'Recuperar Contraseña'.\n" +
                "5. Pulsa 'Iniciar Sesión' para acceder."
        );
        ayuda.showAndWait();
    }
}

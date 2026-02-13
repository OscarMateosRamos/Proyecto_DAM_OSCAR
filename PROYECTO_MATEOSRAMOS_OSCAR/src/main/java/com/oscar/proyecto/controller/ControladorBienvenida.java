package com.oscar.proyecto.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

@Component
public class ControladorBienvenida implements Initializable {

    @Autowired
    private StageManager stageManager;

    @FXML
    private ImageView imgCirco;

    @FXML
    private Button iniciarSesionButton;

    @FXML
    private VBox rootPane; // Asegúrate de que el VBox en el FXML tenga fx:id="rootPane"

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Cargar la imagen
        cargarImagen();

        // Cargar los estilos CSS
        cargarEstilos();
    }

    private void cargarImagen() {
        try (InputStream inputStream = getClass().getResourceAsStream("/images/LogoColegio.png")) {
            if (inputStream != null) {
                imgCirco.setImage(new Image(inputStream));
            } else {
                System.err.println("Imagen NO encontrada en: /img/circo.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarEstilos() {
        try {
            
            String cssPath = FxmlView.BIENVENIDA.getCssFile();
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
        System.out.println("Click en Iniciar Sesión");
         stageManager.switchScene(FxmlView.LOGIN);
    }

   
}

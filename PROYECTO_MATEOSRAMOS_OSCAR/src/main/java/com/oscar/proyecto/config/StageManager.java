package com.oscar.proyecto.config;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class StageManager {

    private final ApplicationContext springContext;
    private Stage primaryStage;

    public StageManager(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setResizable(false);
    }

    public void switchScene(FxmlView view) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getFxmlFile()));

           
            loader.setControllerFactory(clazz -> {
                try {
                    
                    return springContext.getBean(clazz);
                } catch (Exception e) {
                   
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle(view.getTitle());
            primaryStage.setScene(scene);
            primaryStage.setWidth(900);
            primaryStage.setHeight(900);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void abrirVentanaAyuda(FxmlView view) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getFxmlFile()));

	        loader.setControllerFactory(clazz -> {
	            try {
	                return springContext.getBean(clazz);
	            } catch (Exception e) {
	                try {
	                    return clazz.getDeclaredConstructor().newInstance();
	                } catch (Exception ex) {
	                    throw new RuntimeException(ex);
	                }
	            }
	        });

	        Parent root = loader.load();

	        Scene scene = new Scene(root);

	    
	        scene.getStylesheets().add(
	                getClass().getResource("/styles/ayuda.css").toExternalForm()
	        );

	        Stage ayudaStage = new Stage();
	        ayudaStage.setTitle(view.getTitle());
	        ayudaStage.setScene(scene);
	        ayudaStage.setResizable(false);

	        ayudaStage.initOwner(primaryStage);
	        ayudaStage.initModality(Modality.WINDOW_MODAL);

	        ayudaStage.show();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public <T> T getController(Class<T> controllerClass) {
        return springContext.getBean(controllerClass);
    }

	
}

package com.oscar.proyecto;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

@SpringBootApplication
public class Tarea3DamApplication extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
    	
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String password = "admin";
		String hashedPassword = passwordEncoder.encode(password);
		System.out.println("*Contraseña encriptada: " + hashedPassword);
		
        context = new SpringApplicationBuilder(Tarea3DamApplication.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            StageManager stageManager = context.getBean(StageManager.class);
            stageManager.setPrimaryStage(primaryStage);
            stageManager.switchScene(FxmlView.BIENVENIDA);
        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }
}

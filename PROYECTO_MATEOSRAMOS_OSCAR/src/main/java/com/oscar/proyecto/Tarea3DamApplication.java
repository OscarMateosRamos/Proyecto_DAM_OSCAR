package com.oscar.proyecto;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;

@SpringBootApplication
public class Tarea3DamApplication extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
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

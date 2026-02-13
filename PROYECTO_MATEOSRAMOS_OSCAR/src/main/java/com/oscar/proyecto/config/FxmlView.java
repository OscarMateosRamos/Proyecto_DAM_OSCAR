package com.oscar.proyecto.config;

public enum FxmlView {
    BIENVENIDA("/fxml/Bienvenida.fxml", "Bienvenida", "/styles/Bienvenida.css"),
    LOGIN("/fxml/Login.fxml", "Inicio de Sesion", "/styles/Login.css");

    private final String fxmlFile;
    private final String title;
    private final String cssFile;

    FxmlView(String fxmlFile, String title, String cssFile) {
        this.fxmlFile = fxmlFile;
        this.title = title;
        this.cssFile = cssFile;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }

    public String getTitle() {
        return title;
    }

    public String getCssFile() {
        return cssFile;
    }
}

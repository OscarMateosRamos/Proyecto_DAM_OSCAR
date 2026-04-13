package com.oscar.proyecto.controller;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
public class ControladorAyuda implements Initializable {

private static String temaActual = "Login";

public static void setTema(String tema) {
    tema = tema.toLowerCase();

    if (tema.contains("ayuda")) temaActual = "AyudaView";
    else if (tema.contains("bienvenida")) temaActual = "Bienvenida";
    else if (tema.contains("recuperar")) temaActual = "RecuperarContraseña";
    else if (tema.contains("admin")) temaActual = "MenuAdmin";
    else if (tema.contains("artista") && !tema.contains("carnet")) temaActual = "MenuArtista";
    else if (tema.contains("coordinador")) temaActual = "MenuCoordinador";
    else if (tema.contains("gestion") && tema.contains("ciclo")) temaActual = "GestionCiclo";
    else if (tema.contains("gestion") && tema.contains("curso")) temaActual = "GestionCurso";
    else if (tema.contains("gestion") && tema.contains("empresa")) temaActual = "GestionEmpresa";
    else if (tema.contains("gestion") && tema.contains("estudiantes")) temaActual = "GestionEstudiantes";
    else if (tema.contains("gestion") && tema.contains("formaciones")) temaActual = "GestionFormaciones";
    else if (tema.contains("gestion") && tema.contains("grupo")) temaActual = "GestionGrupo";
    else if (tema.contains("gestion") && tema.contains("personas")) temaActual = "GestionPersonas";
    else if (tema.contains("gestion") && tema.contains("profesores")) temaActual = "GestionProfesores";
    else if (tema.contains("gestion") && tema.contains("tutores")) temaActual = "GestionTutoresEmpresa";
    else if (tema.contains("login")) temaActual = "Login";
    else if (tema.contains("menu") && tema.contains("admin")) temaActual = "MenuAdmin";
    else if (tema.contains("menu") && tema.contains("estudiante")) temaActual = "MenuEstudiante";
    else if (tema.contains("menu") && tema.contains("profesor")) temaActual = "MenuProfesor";
    else if (tema.contains("menu") && tema.contains("tutor")) temaActual = "MenuTutor";
    else temaActual = tema;
}

@FXML
private ListView<String> listaTemas;

@FXML
private VBox contenidoAyuda;

private final Map<String, String> ayudas = Map.ofEntries(
    Map.entry("AyudaView", """
        AYUDA GENERAL

        • Selecciona un tema de la lista para ver la ayuda correspondiente.
        • Utiliza esta ventana para resolver dudas sobre el funcionamiento de la aplicación.
        """),

    Map.entry("Bienvenida", """
        AYUDA BIENVENIDA

        • Desde aquí puedes acceder al login.
        • También puedes obtener información general sobre la aplicación.
        """),

    Map.entry("RecuperarContraseña", """
        AYUDA RECUPERAR CONTRASEÑA

        • Introduce tu usuario o correo electrónico.
        • Pulsa “Enviar” para recibir instrucciones de recuperación.
        """),

    Map.entry("Login", """
        AYUDA INICIO DE SESIÓN

        • Introduce tu usuario y contraseña.
        • Pulsa “Iniciar Sesión” para acceder.
        • Usa la opción de recuperación de contraseña si es necesario.
        """),

    Map.entry("MenuAdmin", """
        AYUDA MENÚ ADMINISTRADOR

        • Gestiona usuarios, cursos, empresas y grupos.
        • Accede a las diferentes secciones de administración.
        • Cierra sesión cuando termines.
        """),

    Map.entry("MenuArtista", """
        AYUDA MENÚ ARTISTA

        • Sube tus obras y gestiona tu perfil.
        • Consulta tus solicitudes y eventos asignados.
        """),

    Map.entry("MenuCoordinador", """
        AYUDA MENÚ COORDINADOR

        • Revisa y aprueba solicitudes de artistas.
        • Gestiona eventos y asignaciones.
        """),

    Map.entry("GestionCiclo", """
        AYUDA GESTIÓN DE CICLOS

        • Crea, modifica o elimina ciclos formativos.
        • Asigna cursos a cada ciclo.
        """),

    Map.entry("GestionCurso", """
        AYUDA GESTIÓN DE CURSOS

        • Gestiona la información de los cursos disponibles.
        • Asigna profesores y grupos a cada curso.
        """),

    Map.entry("GestionEmpresa", """
        AYUDA GESTIÓN DE EMPRESAS

        • Registra y gestiona empresas colaboradoras.
        • Asigna tutores y oportunidades de prácticas.
        """),

    Map.entry("GestionEstudiantes", """
        AYUDA GESTIÓN DE ESTUDIANTES

        • Registra y gestiona la información de los estudiantes.
        • Asigna grupos y tutores.
        """),

    Map.entry("GestionFormaciones", """
        AYUDA GESTIÓN DE FORMACIONES

        • Crea y gestiona programas de formación.
        • Asigna recursos y fechas.
        """),

    Map.entry("GestionGrupo", """
        AYUDA GESTIÓN DE GRUPOS

        • Crea y gestiona grupos de estudiantes.
        • Asigna cursos y profesores a cada grupo.
        """),

    Map.entry("GestionPersonas", """
        AYUDA GESTIÓN DE PERSONAS

        • Registra y gestiona usuarios (profesores, estudiantes, tutores).
        • Asigna roles y permisos.
        """),

    Map.entry("GestionProfesores", """
        AYUDA GESTIÓN DE PROFESORES

        • Registra y gestiona la información de los profesores.
        • Asigna cursos y grupos.
        """),

    Map.entry("GestionTutoresEmpresa", """
        AYUDA GESTIÓN DE TUTORES DE EMPRESA

        • Registra y gestiona tutores de empresa.
        • Asigna estudiantes y seguimiento de prácticas.
        """),

    Map.entry("MenuEstudiante", """
        AYUDA MENÚ ESTUDIANTE

        • Consulta tus cursos, notas y horarios.
        • Accede a recursos y materiales de estudio.
        """),

    Map.entry("MenuProfesor", """
        AYUDA MENÚ PROFESOR

        • Gestiona tus cursos y grupos asignados.
        • Registra notas y asistencia de estudiantes.
        """),

    Map.entry("MenuTutor", """
        AYUDA MENÚ TUTOR

        • Realiza seguimiento de estudiantes en prácticas.
        • Registra evaluaciones y comentarios.
        """)
);

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    listaTemas.getItems().addAll(ayudas.keySet());
    listaTemas.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
        mostrarContenido(newV);
    });
    mostrarContenido(temaActual);
}

private void mostrarContenido(String tema) {
    contenidoAyuda.getChildren().clear();
    Label titulo = new Label("Ayuda: " + tema);
    titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    Label texto = new Label(ayudas.getOrDefault(tema, "No hay ayuda disponible para este tema."));
    texto.setWrapText(true);
    contenidoAyuda.getChildren().addAll(titulo, texto);
}

@FXML
private void cerrarAyuda() {
    contenidoAyuda.getScene().getWindow().hide();
}

public static void setArchivoAyuda(String tema) {
    setTema(tema);
}
}



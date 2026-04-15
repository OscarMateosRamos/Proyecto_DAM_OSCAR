package com.oscar.proyecto.config;

public enum FxmlView {
	BIENVENIDA("/fxml/Bienvenida.fxml", "Bienvenida", "/styles/Bienvenida.css"),
	LOGIN("/fxml/Login.fxml", "Inicio de Sesion", "/styles/Login.css"),
	MENUADMIN("/fxml/MenuAdmin.fxml", "Menu Admin", "/styles/MenuAdmin.css"),
	MENUESTUDIANTE("/fxml/MenuEstudiante.fxml", "Menu  Estudiante", "/styles/MenuEstudiante.css"),
	MENUPROFESOR("/fxml/MenuProfesor.fxml", "Menu  Profesor", "/styles/MenuProfesor.css"),
	MENUTUTOREMPRESA("/fxml/MenuTutor.fxml", "Menu  Profesor", "/styles/MenuProfesor.css"),
	GESTIONPROFESORES("/fxml/GestionProfesores.fxml", "Gestion de Profesores", "/styles/GestionProfesores.css"),
	GESTIONPERSONAS("/fxml/GestionPersonas.fxml", "Gestion de Personas", "/styles/GestionPersona.css"),
	GESTIONESTUDIANTES("/fxml/GestionEstudiantes.fxml", "Gestion de Estudiantes", "/styles/GestionEstudiantes.css"),
	GESTIONTUTORESEMPRESA("/fxml/GestionTutoresEmpresa.fxml", "Gestion de Tutores", "/styles/GestionTutores.css"),
	GESTIONEMPRESAS("/fxml/GestionEmpresa.fxml", "Gestion de Empresas", "/styles/GestionEmpresas.css"),
	GESTIONCICLOS("/fxml/GestionCiclo.fxml", "Gestion de Ciclo", "/styles/GestionEmpresas.css"),
	GESTIONCURSOS("/fxml/GestionCurso.fxml", "Gestion de Cursos", "/styles/GestionCurso.css"),
	GESTIONGRUPOS("/fxml/GestionGrupo.fxml", "Gestion de Grupo", "/styles/GestionGrupos.css"),
	GESTIONFORMACIONES("/fxml/GestionFormaciones.fxml", "Gestion de Formaciones", "/styles/GestionGrupos.css"),
	AYUDA("/fxml/AyudaView.fxml", "Ayuda", "/styles/ayuda.css")

	
	

	;

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

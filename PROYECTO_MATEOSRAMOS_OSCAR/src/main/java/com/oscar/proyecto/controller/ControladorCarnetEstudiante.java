package com.oscar.proyecto.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oscar.proyecto.config.FxmlView;
import com.oscar.proyecto.config.StageManager;
import com.oscar.proyecto.modelo.Estudiante;
import com.oscar.proyecto.modelo.Persona;
import com.oscar.proyecto.modelo.Perfil;
import com.oscar.proyecto.services.ServicioEstudiante;
import com.oscar.proyecto.services.ServicioSesion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Component
public class ControladorCarnetEstudiante {

	@Autowired
	private ServicioSesion servicioSesion;

	@Autowired
	private ServicioEstudiante servicioEstudiante;

	@Autowired
	private StageManager stageManager;

	@FXML
	private ImageView fotoEstudiante;

	@FXML
	private Label labelNombre;
	@FXML
	private Label labelApellidos;
	@FXML
	private Label labelEmail;
	@FXML
	private Label labelTelefono;
	@FXML
	private Label labelDni;
	@FXML
	private Label labelCurso;
	@FXML
	private Label labelGrupo;

	private Estudiante est;

	@FXML
	public void initialize() {

		Optional<Persona> personaEnSesion = servicioSesion.getPersonaActual();

		if (personaEnSesion.isEmpty()) {
			System.err.println("ERROR: No hay persona en sesión.");
			return;
		}

		Persona persona = personaEnSesion.get();

		if (!persona.getPerfil().equals(Perfil.ESTUDIANTE)) {
			System.err.println("ERROR: La persona en sesión no es un estudiante.");
			return;
		}

		est = servicioEstudiante.obtenerEstudiantePorPersonaId(persona.getIdPersona());

		if (est == null) {
			System.err.println("ERROR: No existe estudiante con idPersona = " + persona.getIdPersona());
			return;
		}

		labelNombre.setText("Nombre: " + est.getNombre());
		labelApellidos.setText("Apellidos: " + est.getApellidos());
		labelEmail.setText("Email: " + est.getEmail());
		labelTelefono.setText("Teléfono: " + est.getTelefono());
		labelDni.setText("DNI: " + est.getDni());
		labelCurso.setText("Curso: " + est.getCurso());
		labelGrupo.setText("Grupo: " + (est.getGrupo() != null ? est.getGrupo().getNombre() : "Sin grupo"));

		try {
			fotoEstudiante.setImage(new Image(getClass().getResource("/images/Estudiante.png").toExternalForm()));
		} catch (Exception e) {
			System.out.println("No se pudo cargar Estudiante.png");
		}
	}

	@FXML
	private void exportarPDF() {

		Optional<Persona> personaEnSesion = servicioSesion.getPersonaActual();

		if (personaEnSesion.isEmpty()) {
			System.err.println("ERROR: No hay persona en sesión.");
			return;
		}

		Persona persona = personaEnSesion.get();

		if (!persona.getPerfil().equals(Perfil.ESTUDIANTE)) {
			System.err.println("ERROR: La persona en sesión no es un estudiante.");
			return;
		}

		Estudiante est = servicioEstudiante.obtenerEstudiantePorPersonaId(persona.getIdPersona());

		if (est == null) {
			System.err.println("ERROR: Estudiante no encontrado para exportar PDF.");
			return;
		}

		try (PDDocument doc = new PDDocument()) {

			PDPage page = new PDPage();
			doc.addPage(page);

			PDPageContentStream cs = new PDPageContentStream(doc, page);

			cs.beginText();
			cs.setFont(PDType1Font.HELVETICA_BOLD, 20);
			cs.newLineAtOffset(50, 750);
			cs.showText("CARNET DEL ESTUDIANTE");
			cs.endText();

			try {
				InputStream is = getClass().getResourceAsStream("/images/Estudiante.png");

				if (is == null) {
					System.err.println("No se encontró la imagen en /images/Estudiante.png");
				} else {
					BufferedImage buffered = ImageIO.read(is);

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(buffered, "png", baos);

					PDImageXObject pdImage = PDImageXObject.createFromByteArray(doc, baos.toByteArray(),
							"fotoEstudiante");

					cs.drawImage(pdImage, 400, 650, 120, 120);
				}

			} catch (Exception e) {
				System.err.println("Error cargando imagen: " + e.getMessage());
			}

			cs.beginText();
			cs.setFont(PDType1Font.HELVETICA, 14);
			cs.newLineAtOffset(50, 700);
			cs.showText("Nombre: " + est.getNombre());
			cs.endText();

			cs.beginText();
			cs.newLineAtOffset(50, 675);
			cs.showText("Apellidos: " + est.getApellidos());
			cs.endText();

			cs.beginText();
			cs.newLineAtOffset(50, 650);
			cs.showText("Email: " + est.getEmail());
			cs.endText();

			cs.beginText();
			cs.newLineAtOffset(50, 625);
			cs.showText("Teléfono: " + est.getTelefono());
			cs.endText();

			cs.beginText();
			cs.newLineAtOffset(50, 600);
			cs.showText("DNI: " + est.getDni());
			cs.endText();

			cs.beginText();
			cs.newLineAtOffset(50, 575);
			cs.showText("Curso: " + est.getCurso());
			cs.endText();

			cs.beginText();
			cs.newLineAtOffset(50, 550);
			cs.showText("Grupo: " + (est.getGrupo() != null ? est.getGrupo().getNombre() : "Sin grupo"));
			cs.endText();

			cs.close();

			File archivo = new File("Carnet_" + est.getNombre() + ".pdf");
			doc.save(archivo);

			System.out.println("PDF generado correctamente: " + archivo.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void volver() {
		stageManager.switchScene(FxmlView.MENUESTUDIANTE);
	}
}

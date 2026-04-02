package umu.tds.gestor.vista;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PruebaViewController {

	private static final Logger log = LogManager.getLogger();
	
	@FXML
	private TextField categoria;
	
	@FXML
	private DatePicker fecha;
	
	@FXML
	private TextField cantidad;	
	
	
	@FXML
	private void mostrarDatos() throws IOException{
		System.out.println("Categoría " + categoria.getText() + " fecha " + fecha.getValue().toString() + " cantidad " + cantidad.getText());
	}
}

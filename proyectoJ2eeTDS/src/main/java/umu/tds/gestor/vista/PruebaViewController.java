package umu.tds.gestor.vista;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.CategoriaImpl;

public class PruebaViewController {

	private static final Logger log = LogManager.getLogger();
	
	private ControladorGestion controlador;
	
	@FXML
	private TextField categoria;
	
	@FXML
	private DatePicker fecha;
	
	@FXML
	private TextField cantidad;	
	
	@FXML
	public void initialize() {
		this.controlador = Configuracion.getInstancia().getControladorGestion();
	}
	
	@FXML
	private void crearGasto() throws IOException{
		System.out.println(categoria.getText() + fecha.getValue().toString() + cantidad.getText());
		controlador.crearGasto(new CategoriaImpl(categoria.getText()), fecha.getValue(), Double.parseDouble(cantidad.getText()));
		
	}
}

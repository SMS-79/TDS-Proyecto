package umu.tds.gestor.vista;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.CategoriaImpl;

public class AddGastoViewPopUpController{

	private static final Logger log = LogManager.getLogger();
	
	private ControladorGestion controlador = new ControladorGestion();
	
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
	private void crearGasto(ActionEvent event) throws IOException{
		try {
			String textoCategoria = categoria.getText();
			LocalDate fechaGasto = fecha.getValue(); 
			double precio = Double.parseDouble(cantidad.getText());
			
			CategoriaImpl cat = new CategoriaImpl(textoCategoria);
			
			controlador.crearGasto(cat, fechaGasto, precio);
			System.out.println("Gasto guardado exitosamente.");
			
			Configuracion.getInstancia().getSceneManager().mostrarTablaGastos();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			
		} catch (IllegalArgumentException e) {
			System.err.println("Error al crear el gasto: " + e.getMessage());
		}
		
		
	}
}

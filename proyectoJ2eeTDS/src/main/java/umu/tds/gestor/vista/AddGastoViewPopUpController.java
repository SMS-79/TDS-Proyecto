package umu.tds.gestor.vista;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.CategoriaImpl;

public class AddGastoViewPopUpController{

	private static final Logger log = LogManager.getLogger();
	
	private ControladorGestion controlador;
	
	@FXML
	private ChoiceBox<CategoriaImpl> boxCategorias;
	
	@FXML
	private DatePicker fecha;
	
	@FXML
	private TextField cantidad;	
	
	@FXML
	public void initialize() {
		this.controlador = Configuracion.getInstancia().getControladorGestion();
		cargarCategoriasEnBox();
	}
	
	@FXML
	private void crearGasto(ActionEvent event) throws IOException{
		try {
			CategoriaImpl cat = boxCategorias.getValue();
			LocalDate fechaGasto = fecha.getValue(); 
			double precio = Double.parseDouble(cantidad.getText());
			
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
	
	public void cargarCategoriasEnBox() {
		if(controlador.getCategorias() != null) {
			ObservableList<CategoriaImpl> listaModif = FXCollections.observableArrayList(controlador.getCategorias()); // Convertir la tabla de gastos en una que se pueda modificar por JavaFX
			boxCategorias.setItems(listaModif);
		}
	}
}

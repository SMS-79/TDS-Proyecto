package umu.tds.gestor.vista;

import java.io.IOException;
import java.time.LocalDate;
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
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.Categoria;

public class EditGastoViewPopUpController{

	
	private ControladorGestion controlador;
	
	private Gasto gastoSelected;
	
	@FXML
	private ChoiceBox<Categoria> boxCategorias;
	
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
	private void editarGasto(ActionEvent event) throws IOException{
		try {
			Categoria cat = boxCategorias.getValue();
			LocalDate fechaGasto = fecha.getValue(); 
			double precio = Double.parseDouble(cantidad.getText());
			
			controlador.editarGasto(gastoSelected, precio, cat, fechaGasto);
			
			System.out.println("Gasto editado exitosamente.");
			
			Configuracion.getInstancia().getSceneManager().mostrarTablaGastos();
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
		} catch (LimiteAlertaException e){
			Configuracion.getInstancia().getSceneManager().saltarAlerta(e);
			
			Configuracion.getInstancia().getSceneManager().mostrarTablaGastos();
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
	
		} catch (IllegalArgumentException e) {
			System.err.println("Error al editar el gasto: " + e.getMessage());
			System.err.println(e.getCause());
	
		}
	}
	
	public void cargarCategoriasEnBox() {
		if(controlador.getCategorias() != null) {
			ObservableList<Categoria> listaModif = FXCollections.observableArrayList(controlador.getCategorias()); // Convertir la tabla de gastos en una que se pueda modificar por JavaFX
			boxCategorias.setItems(listaModif);
		}
	}
	
	public void setGasto(Gasto g) {
		this.gastoSelected = g;
		this.boxCategorias.setValue(g.getCategoria());
		this.fecha.setValue(g.getFecha());
		this.cantidad.setText(Double.toString(g.getCantidad()));
	}
}

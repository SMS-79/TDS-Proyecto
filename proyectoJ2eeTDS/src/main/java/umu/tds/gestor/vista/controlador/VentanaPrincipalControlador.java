package umu.tds.gestor.vista.controlador;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.GastoImpl;

public class VentanaPrincipalControlador {
	
	private ControladorGestion controlador = new ControladorGestion(); 
	
	@FXML private DatePicker dpFechaInicio; 
	@FXML private DatePicker dpFechaFin; 
	@FXML private TableView<GastoImpl> tablaGastos; 
	
	@FXML
	public void botonBuscar(ActionEvent evento) { // método con el que enlazamos On Action de SceneBuilder
		
		Button botonPulsado = (Button) evento.getSource();
		System.out.println("Has pulsado el boton: " + botonPulsado); // usamos este mensaje para saber que botón estamos pulsando, se puede usar el mismo metodo pra distintos botones
		LocalDate inicio = dpFechaInicio.getValue(); 
		LocalDate fin = dpFechaFin.getValue(); 
		
		List<? extends GastoImpl> resultados = controlador.filtrarGastos(null, inicio, fin, null);
		tablaGastos.getItems().setAll(resultados);
		
		
	}
	
	@FXML
	public void botonCerrar(ActionEvent event) {
	    // sacamos el botón del evento, luego su escena, y luego su ventana, y la cerramos
	    Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}

	
	
	

}

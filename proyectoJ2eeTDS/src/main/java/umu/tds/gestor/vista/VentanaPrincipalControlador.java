package umu.tds.gestor.vista;

import java.awt.Button;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;

public class VentanaPrincipalControlador {
	
	private static final Logger log = LogManager.getLogger();
	
	private ControladorGestion controlador; 
	
	private GastoImpl gastoSeleccionado;
	
	@FXML 
	private DatePicker dpFechaInicio; 
	
	@FXML 
	private DatePicker dpFechaFin; 
	
	@FXML 
	public TableView<GastoImpl> tablaGastos = new TableView<GastoImpl>(); 
	
	
	@FXML
	private TableColumn<GastoImpl, UUID> colID;
	@FXML
	private TableColumn<GastoImpl, CategoriaImpl> colCategoria;
	@FXML
	private TableColumn<GastoImpl, LocalDate> colFecha;
	@FXML
	private TableColumn<GastoImpl, Double> colCantidad;
	
	
	@FXML
	public void initialize() {
		this.controlador = Configuracion.getInstancia().getControladorGestion();
		
		for(GastoImpl g : controlador.getGastos()) {
			aniadirGasto(g);
		}
		
		tablaGastos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.gastoSeleccionado = newSelection;
			}
		});
		
		colID.setCellValueFactory(new PropertyValueFactory<>("idGasto"));
		colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
		
		ObservableList<GastoImpl> lista = null;
		try {
			lista = FXCollections.observableArrayList(new ArrayList<GastoImpl>());
		} catch (Exception e) {
			log.error("Error inicializando TiendaViewController", e);
		}
		
		//tablaGastos.setItems(lista);	
		
	}
	
	@FXML
	private void botonBuscar(ActionEvent evento) { // método con el que enlazamos On Action de SceneBuilder
		
		Button botonPulsado = (Button) evento.getSource();
		System.out.println("Has pulsado el boton: " + botonPulsado); // usamos este mensaje para saber que botón estamos pulsando, se puede usar el mismo metodo pra distintos botones
		LocalDate inicio = dpFechaInicio.getValue(); 
		LocalDate fin = dpFechaFin.getValue(); 
		
		List<? extends GastoImpl> resultados = controlador.filtrarGastos(null, inicio, fin, null);
		tablaGastos.getItems().setAll(resultados);
		
		
	}
	
	@FXML
	private void botonCerrar(ActionEvent event) {
	    // sacamos el botón del evento, luego su escena, y luego su ventana, y la cerramos
	    Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
	public void aniadirGasto(GastoImpl g) {
		System.out.println(g.toString());
		
		tablaGastos.getItems().add(g);
		tablaGastos.refresh();
		
	}

}

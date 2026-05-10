package umu.tds.gestor.vista;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.GastoImpl;

public class ListaGastosViewController {
	
	
	private ControladorGestion controlador; 
	
	private GastoImpl gastoSeleccionado;
	
	@FXML
	private TextField categoriaFilter;
	
	@FXML
	private MenuButton menuMeses; 
	
	@FXML 
	private TextField precio;
	
	@FXML 
	private DatePicker dpFechaInicio; 
	
	@FXML 
	private DatePicker dpFechaFin; 
	
	@FXML 
	public TableView<GastoImpl> tablaGastos; 
	
	
	@FXML
	private TableColumn<GastoImpl, UUID> colID;
	@FXML
	private TableColumn<GastoImpl, Categoria> colCategoria;
	@FXML
	private TableColumn<GastoImpl, LocalDate> colFecha;
	@FXML
	private TableColumn<GastoImpl, Double> colCantidad;
	
	@FXML
	private Button botonBorrar;
	
	@FXML
	private Button botonEditar;
	
	
	@FXML
	public void initialize() {
		this.controlador = Configuracion.getInstancia().getControladorGestion();
		
		colID.setCellValueFactory(new PropertyValueFactory<>("idGasto"));
		colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
		
		// Lista para filtrar por meses especificos
		String[] nombreMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
		        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		for (String mes : nombreMeses) {
			CheckMenuItem item = new CheckMenuItem(mes);
			menuMeses.getItems().add(item); 
		}
		
		cargarDatosEnTabla();
		
		
		tablaGastos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.gastoSeleccionado = newSelection;
			}
		});
		
		botonBorrar.disableProperty().bind(tablaGastos.getSelectionModel().selectedItemProperty().isNull());
		botonEditar.disableProperty().bind(tablaGastos.getSelectionModel().selectedItemProperty().isNull());
	}
	
	public void cargarDatosEnTabla() { // Método para recargar la tabla cuando queramos
		if(controlador.getGastos() != null) {
			ObservableList<GastoImpl> listaModif = FXCollections.observableArrayList(controlador.getGastos()); // Convertir la tabla de gastos en una que se pueda modificar por JavaFX
			tablaGastos.setItems(listaModif);
		}
	}
	
	// Para cuando se hagan los repositorios de categoría
	
	@FXML
	private void buscar(ActionEvent evento) { // Método con el que enlazamos On Action de SceneBuilder
		
		System.out.println("Has pulsado el boton de buscar"); // Usamos este mensaje para saber que botón estamos pulsando, se puede usar el mismo metodo pra distintos botones
		List<? extends Categoria> categorias = null;
		
		if(!categoriaFilter.getText().equals("")) {
			categorias = controlador.filtrarCategoria(categoriaFilter.getText());
		}
		LocalDate inicio = dpFechaInicio.getValue(); 
		LocalDate fin = dpFechaFin.getValue(); 
		
		List<Month> mesesFiltro = new ArrayList<>();
		
		for (MenuItem item : menuMeses.getItems()) {
			CheckMenuItem checkItem = (CheckMenuItem) item; 
			
			if (checkItem.isSelected()) {
					switch (checkItem.getText()) {
	                case "Enero": mesesFiltro.add(Month.JANUARY); break;
	                case "Febrero": mesesFiltro.add(Month.FEBRUARY); break;
	                case "Marzo": mesesFiltro.add(Month.MARCH); break;
	                case "Abril": mesesFiltro.add(Month.APRIL); break;
	                case "Mayo": mesesFiltro.add(Month.MAY); break;
	                case "Junio": mesesFiltro.add(Month.JUNE); break;
	                case "Julio": mesesFiltro.add(Month.JULY); break;
	                case "Agosto": mesesFiltro.add(Month.AUGUST); break;
	                case "Septiembre": mesesFiltro.add(Month.SEPTEMBER); break;
	                case "Octubre": mesesFiltro.add(Month.OCTOBER); break;
	                case "Noviembre": mesesFiltro.add(Month.NOVEMBER); break;
	                case "Diciembre": mesesFiltro.add(Month.DECEMBER); break;
	            }
			}
		}
		
		if(mesesFiltro.isEmpty()) {
			mesesFiltro = null; 
		}
		
		List<? extends GastoImpl> resultados = controlador.filtrarGastos(mesesFiltro, inicio, fin, categorias);
		tablaGastos.getItems().setAll(resultados);
		
		if(resultados != null) {
			tablaGastos.setItems(FXCollections.observableArrayList(resultados));
		}
	}
	
	@FXML
	private void borrar(ActionEvent evento) {
		controlador.borrarGasto(gastoSeleccionado);
		tablaGastos.getItems().remove(gastoSeleccionado);
		tablaGastos.getSelectionModel().clearSelection();
	}
	
	@FXML
	private void editar(ActionEvent evento) {
		Configuracion.getInstancia().getSceneManager().mostrarEditarGasto(gastoSeleccionado);
		tablaGastos.getSelectionModel().clearSelection();
	}
	

}

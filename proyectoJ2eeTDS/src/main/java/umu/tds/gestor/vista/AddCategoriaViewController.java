package umu.tds.gestor.vista;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.Categoria;

public class AddCategoriaViewController {

	private static final Logger log = LogManager.getLogger();
	
	private ControladorGestion controlador; 
	
	private Categoria categoriaSeleccionada;
	
	@FXML
	private TextField categoriaId;
	
	@FXML
	private TableView<Categoria> tablaCategorias;
	
	@FXML
	private TableColumn<Categoria, String> colCategoria;
	
	
	
	@FXML
	public void initialize() {
		this.controlador = Configuracion.getInstancia().getControladorGestion();
		
		colCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		
		cargarDatosEnTabla();
		
		tablaCategorias.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.categoriaSeleccionada = newSelection;
			}
		});
		
		ObservableList<Categoria> lista = null;
		try {
			lista = FXCollections.observableArrayList(new ArrayList<Categoria>());
		} catch (Exception e) {
			log.error("Error inicializando TiendaViewController", e);
		}
		
		
	}
	
	public void cargarDatosEnTabla() { // Método para recargar la tabla cuando queramos
		if(controlador.getCategorias() != null) {
			ObservableList<Categoria> listaModif = FXCollections.observableArrayList(controlador.getCategorias()); // Convertir la tabla de gastos en una que se pueda modificar por JavaFX
			tablaCategorias.setItems(listaModif);
		}
	}
	
	@FXML
	private void crearCategoria() {
		if(!categoriaId.getText().equals("")){
			Categoria cat = controlador.crearCategoria(categoriaId.getText());
			addCategoriaTabla(cat);
			categoriaId.clear();
		}
	}
	
	private void addCategoriaTabla(Categoria categoria) {
		if(!tablaCategorias.getItems().contains(categoria)) {
			tablaCategorias.getItems().add(categoria);
		}
		
		tablaCategorias.refresh();
	}
}

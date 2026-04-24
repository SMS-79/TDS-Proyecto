package umu.tds.gestor.vista;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.Notificacion;

public class HistorialNotificacionesViewController {
	private static final Logger log = LogManager.getLogger();
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	private ControladorGestion controlador; 
	
	private Notificacion notificacionSeleccionada;
	
	@FXML
	private TableView<Notificacion> tablaNotif;
	
	@FXML
	private TableColumn<Notificacion, UUID> colId;
	
	@FXML
	private TableColumn<Notificacion, String> colMessage;

	@FXML
	private TableColumn<Notificacion, String> colDate;
	

	
	@FXML
	public void initialize() {
		this.controlador = Configuracion.getInstancia().getControladorGestion();
		
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colMessage.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
		colDate.setCellValueFactory(cellData -> {
			LocalDateTime fecha = cellData.getValue().getFechaCreacion();
			String fechaFormatted = fecha.format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm"));
			return new SimpleStringProperty(fechaFormatted);
		});
		
		cargarDatosEnTabla();
		
		tablaNotif.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				this.notificacionSeleccionada = newSelection;
			}
		});
		
		ObservableList<Notificacion> lista = null;
		try {
			lista = FXCollections.observableArrayList(new ArrayList<Notificacion>());
		} catch (Exception e) {
			log.error("Error inicializando TiendaViewController", e);
		}
	}
	
	public void cargarDatosEnTabla() { // Método para recargar la tabla cuando queramos
		if(controlador.getNotifs() != null) {
			ObservableList<Notificacion> listaModif = FXCollections.observableArrayList(controlador.getNotifs()); // Convertir la tabla de gastos en una que se pueda modificar por JavaFX
			tablaNotif.setItems(listaModif);
		}
	}
	
	
}

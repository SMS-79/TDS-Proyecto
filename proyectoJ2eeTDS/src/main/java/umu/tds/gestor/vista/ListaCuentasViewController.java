package umu.tds.gestor.vista;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.CuentaGasto;
import umu.tds.gestor.modelo.impl.GastoImpl;

public class ListaCuentasViewController {

	private ControladorGestion controlador;
	
	private CuentaGasto cuentaSeleccionada;
	
    @FXML
    private Button botonBorrar;
    
    @FXML
    private Button botonIntroducirPago;

    @FXML
    private TableColumn<CuentaGasto, Double> colGasto;

    @FXML
    private TableColumn<CuentaGasto, UUID> colID;

    @FXML
    private TableColumn<CuentaGasto, List<String>> colParticipantes;

    @FXML
    private TextField nombresFiltro;

    @FXML
    private TableColumn<DetalleMiembro, String> propParticipante;

    @FXML
    private TableColumn<DetalleMiembro, Double> propPorcentaje;

    @FXML
    private TableColumn<DetalleMiembro, Double> propSaldo;

    @FXML
    private TableView<CuentaGasto> tablaCuentas;
    
    @FXML
    private TableView<DetalleMiembro> tablaDistribuciones;

    public void initialize() {
		this.controlador = Configuracion.getInstancia().getControladorGestion();
		
		colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		colParticipantes.setCellValueFactory(new PropertyValueFactory<>("participantes"));
		colGasto.setCellValueFactory(new PropertyValueFactory<>("gastoAsociado"));
		
		propParticipante.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		propPorcentaje.setCellValueFactory(new PropertyValueFactory<>("porcentaje"));
		propSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
		
		cargarTablaCuentas();
		
		botonBorrar.disableProperty().bind(tablaCuentas.getSelectionModel().selectedItemProperty().isNull());
		botonIntroducirPago.disableProperty().bind(tablaCuentas.getSelectionModel().selectedItemProperty().isNull());
		
		//Añadimos un listener para detectar cuando cambiamos de seleccion, y cuando lo hagamos llamamos a la funcion para actualizar la tabla de Distribuciones
	    tablaCuentas.getSelectionModel().selectedItemProperty().addListener((contenedor, seleccionAnterior, seleccionActual) -> {
	    	this.cuentaSeleccionada = seleccionActual;
	        if (cuentaSeleccionada != null) {
	        	mostrarTablaDistribuciones(cuentaSeleccionada);
	        } else {
	            tablaDistribuciones.getItems().clear();
	        }
	    });
	    cargarTablaCuentas();
    }
    
    @FXML
    void borrarCuenta(ActionEvent event) {

    	controlador.borrarCuenta(cuentaSeleccionada);
    	tablaCuentas.getItems().remove(cuentaSeleccionada);
    	tablaCuentas.getSelectionModel().clearSelection();
    }

    @FXML
    void botonFiltrar(ActionEvent event) {

    }

    @FXML
    void crearCuenta(ActionEvent event) {
    	Configuracion.getInstancia().getSceneManager().mostrarAddCuenta();
    }

    @FXML
    void introducirPago(ActionEvent event) {

    }
    
    //Con este metodo podemos recargar la tabla, por ejemplo cuando añadamos una cuenta
    public void cargarTablaCuentas() {
		if(controlador.getCuentas() != null) {
			//Transformamos la lista a ObservableList para que pueda leerla javaFX
			ObservableList<CuentaGasto> listaCuentas = FXCollections.observableArrayList(controlador.getCuentas());
			tablaCuentas.setItems(listaCuentas);
		}
	}
    
    //Funcion para actualizar la tabla de distribuciones con la cuenta seleccionada
    public void mostrarTablaDistribuciones(CuentaGasto cuenta) {
    	
    	ObservableList<DetalleMiembro> detalles = FXCollections.observableArrayList();
    	
    	Map<String, Double> distribuciones = cuenta.getDistribuciones();
    	Map<String, Double> saldos = cuenta.getSaldos();
    	
    	for(String participante : cuenta.getParticipantes()) {
    		DetalleMiembro miembro = new DetalleMiembro(participante, 
    													distribuciones.get(participante), 
    													saldos.get(participante));
    		detalles.add(miembro);
    	}
    	tablaDistribuciones.setItems(detalles);
    }
    
    //Clase auxiliar para la tabla de Distribuciones
    public static class DetalleMiembro {
        private String nombre;
        private Double porcentaje;
        private Double saldo;
        public DetalleMiembro(String nombre, Double porcentaje, Double saldo) {
            this.nombre = nombre;
            this.porcentaje = porcentaje;
            this.saldo = saldo;
        }
        public String getNombre() { return nombre; }
        public Double getPorcentaje() { return porcentaje; }
        public Double getSaldo() { return saldo; }
    }

}

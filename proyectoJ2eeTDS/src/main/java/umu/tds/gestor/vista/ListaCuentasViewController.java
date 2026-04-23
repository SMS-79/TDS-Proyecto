package umu.tds.gestor.vista;

import java.util.List;
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

public class ListaCuentasViewController {

	private ControladorGestion controlador;
	
    @FXML
    private Button botonBorrar;

    @FXML
    private TableColumn<CuentaGasto, Double> colGasto;

    @FXML
    private TableColumn<CuentaGasto, UUID> colID;

    @FXML
    private TableColumn<CuentaGasto, List<String>> colParticipantes;

    @FXML
    private TextField nombresFiltro;

    @FXML
    private TableColumn<?, ?> propParticipante;

    @FXML
    private TableColumn<?, ?> propPorcentaje;

    @FXML
    private TableColumn<?, ?> propSaldo;

    @FXML
    private TableView<CuentaGasto> tablaCuentas;

    public void initialize() {
		this.controlador = Configuracion.getInstancia().getControladorGestion();
		
		colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		colParticipantes.setCellValueFactory(new PropertyValueFactory<>("participantes"));
		colGasto.setCellValueFactory(new PropertyValueFactory<>("gastoAsociado"));
		
		cargarTablaCuentas();
    }
    
    @FXML
    void borrarCuenta(ActionEvent event) {

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

}

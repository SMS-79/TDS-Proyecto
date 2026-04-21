package umu.tds.gestor.vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import umu.tds.gestor.Configuracion;

public class ListaCuentasViewController {

    @FXML
    private Button botonBorrar;

    @FXML
    private TableColumn<?, ?> colGasto;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colParticipantes;

    @FXML
    private TextField nombresFiltro;

    @FXML
    private TableColumn<?, ?> propParticipante;

    @FXML
    private TableColumn<?, ?> propPorcentaje;

    @FXML
    private TableColumn<?, ?> propSaldo;

    @FXML
    private TableView<?> tablaCuentas;

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

}

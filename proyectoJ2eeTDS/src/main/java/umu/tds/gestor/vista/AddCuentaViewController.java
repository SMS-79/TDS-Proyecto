package umu.tds.gestor.vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddCuentaViewController {

    @FXML
    private TableColumn<?, ?> colGasto;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colParticipantes;

    @FXML
    private TextField participantes;

    @FXML
    private TableColumn<?, ?> propParticipante;

    @FXML
    private TableColumn<?, ?> propPorcentaje;

    @FXML
    private TableView<?> tablaCuentas;

    @FXML
    void crearCuenta(ActionEvent event) {

    }

}

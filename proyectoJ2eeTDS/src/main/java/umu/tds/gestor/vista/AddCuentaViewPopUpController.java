package umu.tds.gestor.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;

public class AddCuentaViewPopUpController {

	private ControladorGestion controlador;
	
    @FXML
    private Button botonBorrarMiembro;

    @FXML
    private TableView<Pair<String, Double>> tablaMiembros;

    @FXML
    private TableColumn<Pair<String, Double>, String> colNombre;

    @FXML
    private TableColumn<Pair<String, Double>, Double> colPorcentaje;

    @FXML
    private TextField gastoCuenta;

    @FXML
    private TextField nombreMiembro;

    @FXML
    private TextField porcentajeMiembro;

    private ObservableList<Pair<String, Double>> miembros = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    	this.controlador = Configuracion.getInstancia().getControladorGestion();
    	
    	//Se pone "key" y "value" porque son los campos de la Pair, getKey y getValue
        colNombre.setCellValueFactory(new PropertyValueFactory<>("key"));
        colPorcentaje.setCellValueFactory(new PropertyValueFactory<>("value"));
        //La tabla mostrará la lista miembros
        tablaMiembros.setItems(miembros);
    }

    @FXML
    void addMiembro(ActionEvent event) {
    	//Conseguimos los datos escritos en los campos
        String nombre = nombreMiembro.getText().trim();
        String porcentajeStr = porcentajeMiembro.getText().trim();

        if (nombre.isEmpty()) {
            return;
        }

        Double porcentaje = null;
        if (!porcentajeStr.isEmpty()) {
            try {
                porcentaje = Double.parseDouble(porcentajeStr);
            } catch (NumberFormatException e) {
                //Salta excepcion y salimos si el numero no tiene un formato válido
                return;
            }
        }

        miembros.add(new Pair<String, Double>(nombre, porcentaje));

        // Borramos los campos rellenados
        nombreMiembro.clear();
        porcentajeMiembro.clear();
    }

    @FXML
    void borrarMiembro(ActionEvent event) {
        Pair<String, Double> selected = tablaMiembros.getSelectionModel().getSelectedItem();
        if (selected != null) {
            miembros.remove(selected);
        }
    }

    @FXML
    void crearCuentaPopUp(ActionEvent event) {
    	try {
	    	Double gasto = Double.parseDouble(gastoCuenta.getText().trim());
	    	//Coge todos lo nombres de la lista de parejas
	    	String[] nombresArr = miembros.stream()
	                				.map(Pair::getKey)
	                				.toArray(String[]::new);
	
	    	if (nombresArr.length == 0) {
	    		System.err.println("Debe haber al menos un miembro para crear la cuenta.");
	    		return;
	    	}
	    	
	    	controlador.crearCuenta(gasto, nombresArr);
	    	
	    	Configuracion.getInstancia().getSceneManager().mostrarTablaCuentas();
	    	
	    	// Cerramos la ventana después de crear la cuenta
	    	Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			
    	} catch (NumberFormatException e) {
    		System.err.println("El gasto de la cuenta debe ser un número válido.");
    	}
    }

}

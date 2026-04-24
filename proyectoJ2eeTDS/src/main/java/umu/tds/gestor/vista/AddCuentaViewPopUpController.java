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
import java.util.LinkedHashMap;
import java.util.Map;



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
    private Boolean isPorcentual = null;


    @FXML
    public void initialize() {
    	this.controlador = Configuracion.getInstancia().getControladorGestion();
    	
        //Se pone "key" y "value" porque son los campos de la Pair, getKey y getValue
        colNombre.setCellValueFactory(new PropertyValueFactory<>("key"));
        colPorcentaje.setCellValueFactory(new PropertyValueFactory<>("value"));

        //La tabla mostrará la lista miembros
        tablaMiembros.setItems(miembros);

        botonBorrarMiembro.disableProperty().bind(tablaMiembros.getSelectionModel().selectedItemProperty().isNull());
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
        
        // Si es el primer miembro, decidimos el modo
        if (miembros.isEmpty()) {
            if (porcentajeStr.isEmpty()) {
                isPorcentual = false;
                porcentajeMiembro.setDisable(true);
            } else {
                isPorcentual = true;
            }
        }
        
        //Comprobamos que se deben introducion porcentajes
        if (isPorcentual) {
            if (porcentajeStr.isEmpty()) {
                System.err.println("Debe introducir un porcentaje en modo porcentual.");
                return;
            }
            try {
                porcentaje = Double.parseDouble(porcentajeStr);
            } catch (NumberFormatException e) {
                return;
            }
        }

        miembros.add(new Pair<String, Double>(nombre, porcentaje));

        //Borramos los campos rellenados
        nombreMiembro.clear();
        porcentajeMiembro.clear();
    }


    @FXML
    void borrarMiembro(ActionEvent event) {
        Pair<String, Double> seleccion = tablaMiembros.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            miembros.remove(seleccion);
            tablaMiembros.getSelectionModel().clearSelection();
            //Si la tabla se queda vacía, reseteamos el modo
            if (miembros.isEmpty()) {
                isPorcentual = null;
                porcentajeMiembro.setDisable(false);
            }
        }
    }


    @FXML
    void crearCuentaPopUp(ActionEvent event) {
        try {
            Double gasto = Double.parseDouble(gastoCuenta.getText().trim());
            
            if (miembros.isEmpty()) {
                System.err.println("Debe haber al menos un miembro para crear la cuenta.");
                return;
            }

            if (isPorcentual) {

                //Comprobamos si todos los porcentajes suman 100
                double suma = 0.0;
                for (Pair<String, Double> p : miembros) {
                    if (p.getValue() != null) {
                        suma += p.getValue();
                    }
                }

                if (suma != 100.0) {
                    System.err.println("La suma de los porcentajes debe ser exactamente 100%. Actual: " + suma + "%");
                    return;
                }
                
                //Usamos LinkedHashMap para mantener el orden (El primero el pagador)
                Map<String, Double> distribuciones = new LinkedHashMap<>();
                for (Pair<String, Double> p : miembros) {
                    distribuciones.put(p.getKey(), p.getValue());
                }
                controlador.crearCuenta(gasto, distribuciones);
                
            } else {
                //Pasamos un mapa con porcentajes nulos para que el controlador decida que cuenta crear
                Map<String, Double> distribuciones = new LinkedHashMap<>();
                for (Pair<String, Double> p : miembros) {
                    distribuciones.put(p.getKey(), null);
                }
                controlador.crearCuenta(gasto, distribuciones);
            }

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

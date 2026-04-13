package umu.tds.gestor.vista;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.Intervalo;


public class AddAlertaViewController {

    private static final Logger log = LogManager.getLogger(); 

    private ControladorGestion controlador; 

    @FXML
    private ComboBox<String> comboFiltro; 

    @FXML
    private TableView<Alerta> tablaAlertas;

    @FXML 
    private TableColumn<Alerta, Intervalo> colNombre; 

    @FXML
    private TableColumn<Alerta, Categoria> colCategoria; 

    @FXML
    private TableColumn<Alerta, Double> colLimite;

    @FXML
    private ComboBox<String> comboTipo; 

    @FXML
    private ComboBox<Categoria> comboCategoria; 

    @FXML
    private TextField campoLimite; 

    private ObservableList<Alerta> listaAlertasObservable; 

    @FXML
    public void initialize(){
        this.controlador = Configuracion.getInstancia().getControladorGestion();
        
        colNombre.setCellValueFactory(new PropertyValueFactory<>("intervalo"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colLimite.setCellValueFactory(new PropertyValueFactory<>("limite"));

        comboFiltro.setItems(FXCollections.observableArrayList("Todas", "Mensual", "Semanal"));
        comboFiltro.setValue("Todas");
        comboTipo.setItems(FXCollections.observableArrayList("Mensual", "Semanal"));

        if(controlador.getCategorias() != null){
            comboCategoria.setItems(FXCollections.observableArrayList(controlador.getCategorias()));
        }

        cargarDatosEnTabla();

        comboFiltro.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            filtrarTabla(newVal);
        });
    }

        private void cargarDatosEnTabla(){
            List<? extends Alerta> alertasDominio = controlador.getAlertas(); 
            if(alertasDominio == null){
                alertasDominio = new ArrayList<>(); 
            }

            listaAlertasObservable = FXCollections.observableArrayList(alertasDominio);
            tablaAlertas.setItems(listaAlertasObservable); 
        }
        private void filtrarTabla(String filtro) {
            if (filtro == null || filtro.equals("Todas")) {
                tablaAlertas.setItems(listaAlertasObservable); 
                return;
            }
            ObservableList<Alerta> filtradas = FXCollections.observableArrayList();
            for (Alerta a : listaAlertasObservable) {
                if (a.getIntervalo().toString().equalsIgnoreCase(filtro)) {
                    filtradas.add(a);
                }
            }
            tablaAlertas.setItems(filtradas);
    }

    @FXML
    private void crearAlerta() {
        try {
            String tipo = comboTipo.getValue();
            Categoria categoria = comboCategoria.getValue(); 
            double limite = Double.parseDouble(campoLimite.getText());

            if (tipo == null || campoLimite.getText().isEmpty()) {
                System.err.println("Faltan campos obligatorios");
                return;
            }

            cargarDatosEnTabla();
            
           
            campoLimite.clear();
            comboTipo.getSelectionModel().clearSelection();
            comboCategoria.getSelectionModel().clearSelection();

            System.out.println("Alerta " + tipo + " creada correctamente.");

        } catch (NumberFormatException e) {
            System.err.println("El límite debe ser un número válido.");
        }
    }
}

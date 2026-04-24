package umu.tds.gestor.vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.controladores.ControladorGestion;
import umu.tds.gestor.modelo.CuentaGasto;

public class AddPagoCuentaViewPopUpController {

	private ControladorGestion controlador;
	private CuentaGasto cuenta;
	
    @FXML
    private ChoiceBox<String> boxMiembros;

    @FXML
    private TextField pagoMiembro;

    @FXML
    private Label saldoActualLabel;

    @FXML
    public void initialize() {
    	this.controlador = Configuracion.getInstancia().getControladorGestion();
    	this.cuenta = controlador.getCuentaSeleccionada();
    	
    	if (cuenta != null) {
    		boxMiembros.getItems().addAll(cuenta.getParticipantes());
    	}
    	
    	//Listener para mostrar el saldo del miembro cuando se selecciona en el selection box
    	boxMiembros.getSelectionModel().selectedItemProperty().addListener((obs, seleccionAnterio, seleccionActual) -> {
    		if(seleccionActual != null) {
    			Double saldo = cuenta.getSaldoMiembro(seleccionActual);
    			saldoActualLabel.setText("Saldo actual: " + saldo.toString());
    		}
    	});
    }
    
    @FXML
    void pagarMiembro(ActionEvent event) {
    	try {
	    	Double pagoCant = Double.parseDouble(pagoMiembro.getText());
	    	String miembroSelec = boxMiembros.getSelectionModel().getSelectedItem();
	    	
	    	if (miembroSelec == null) return;

	    	//Comprobamos si el pago proviene del pagador
	    	if (miembroSelec.equals(cuenta.getNombrePagador())) {
	    		System.err.println("El pagador no puede realizar pagos");
	    		return;
	    	}

	    	//Comprobamos si el pago es superior a nuestra deuda
	    	if (cuenta.getSaldoMiembro(miembroSelec) + pagoCant > 0) {
	    		System.err.println("El pago no puede ser superior a la deuda actual");
	    		return;
	    	}

	    	if (pagoCant > 0) {
		    	boolean pagoRealizado = controlador.realizarPagoCuenta(cuenta, miembroSelec, pagoCant);
		    	
		    	if (pagoRealizado) {
					Node source = (Node) event.getSource();
					Stage stage = (Stage) source.getScene().getWindow();
					stage.close();
		    	} else {
		    		System.err.println("No se ha podido realizar el pago");
		    	}
	    	}
    	} catch (NumberFormatException e) {
    		System.err.println("Formato del número del pago inválido");
    	}
    }

}

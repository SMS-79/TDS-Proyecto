package umu.tds.gestor.vista;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.MainAppFX;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.GastoImpl;

public class SceneManager {
	
	private Stage stage;
	private Scene escenaActual;
	private BorderPane layoutPrincipal; 
	
	
	public void inicializar(Stage stage) {
		this.stage = stage;
	}
	
	public void showVentanaPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader(MainAppFX.class.getResource("BarraMenuView.fxml")); 
			layoutPrincipal = (BorderPane) loader.load();
			
			escenaActual = new Scene(layoutPrincipal, 800, 600); 
			stage.setScene(escenaActual);
			stage.setTitle("Gestor de Gastos");
			stage.show();
			mostrarTablaGastos(); 
			
		} catch (IOException e){
			throw new RuntimeException(e);
		}
	}
	
	private void cambiarVista(String fxml){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(MainAppFX.class.getResource(fxml + ".fxml"));
			Parent nuevaVista = fxmlLoader.load(); 
			
			if (layoutPrincipal != null) {
				layoutPrincipal.setCenter(nuevaVista); 
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void mostrarTablaGastos() {
		cambiarVista("ListaGastosView");
	}
	
	public void mostrarAddGasto() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(MainAppFX.class.getResource("AddGastoViewPopUp.fxml"));
			Parent root = fxmlLoader.load();
			
			Stage popUpAddGasto = new Stage(); 
			popUpAddGasto.setTitle("Añadir gasto nuevo");
			
			popUpAddGasto.initModality(Modality.APPLICATION_MODAL);
			popUpAddGasto.initOwner(this.stage);
			popUpAddGasto.setResizable(false);
			
			Scene scene = new Scene(root);
			popUpAddGasto.setScene(scene);
			popUpAddGasto.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void mostrarEditarGasto(GastoImpl g) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(MainAppFX.class.getResource("EditGastoViewPopUp.fxml"));
			
			Parent root = fxmlLoader.load();
			
			EditGastoViewPopUpController controladorVista =  fxmlLoader.getController();
			controladorVista.setGasto(g);
			
			Stage popUpEditGasto = new Stage();
			popUpEditGasto.setTitle("Editar gasto");
			
			popUpEditGasto.initModality(Modality.APPLICATION_MODAL);
			popUpEditGasto.initOwner(this.stage);
			popUpEditGasto.setResizable(false);
			
			Scene scene = new Scene(root);
			popUpEditGasto.setScene(scene);
			popUpEditGasto.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	

	
	public void mostrarCreadorCategorias() {
		cambiarVista("AddCategoriaView");
	}
	
	public File mostrarSelectorFicheroGastos() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar fichero para importar gastos");
		fileChooser.getExtensionFilters().add(
			new FileChooser.ExtensionFilter("Archivos de gastos", "*.csv", "*.txt")
		);

		return fileChooser.showOpenDialog(this.stage);
	}
	
	public void mostrarAddAlerta(){
		cambiarVista("AddAlertaView");
	}
	
	public void mostrarTablaCuentas() {
		cambiarVista("ListaCuentasView");
	}
	
	public void mostrarGraficoGastos() {
		cambiarVista("GraficoBarrasGasto");
	}
	
	public void mostrarAddCuenta() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(MainAppFX.class.getResource("AddCuentaViewPopUp.fxml"));
			Parent root = fxmlLoader.load();
			
			Stage popUpAddCuenta = new Stage(); 
			popUpAddCuenta.setTitle("Crear nueva cuenta");
			
			popUpAddCuenta.initModality(Modality.APPLICATION_MODAL);
			popUpAddCuenta.initOwner(this.stage);
			popUpAddCuenta.setResizable(false);
			
			Scene scene = new Scene(root);
			popUpAddCuenta.setScene(scene);
			popUpAddCuenta.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void mostrarNotifList(){
		cambiarVista("ListaNotificaciones");
	}
	
	public void mostrarAddPago() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(MainAppFX.class.getResource("AddPagoCuentaViewPopUp.fxml"));
			Parent root = fxmlLoader.load();
			
			Stage popUpAddPago = new Stage(); 
			popUpAddPago.setTitle("Añadir pago");
			
			popUpAddPago.initModality(Modality.APPLICATION_MODAL);
			popUpAddPago.initOwner(this.stage);
			popUpAddPago.setResizable(false);
			
			Scene scene = new Scene(root);
			popUpAddPago.setScene(scene);
			popUpAddPago.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarCalendario() {
		cambiarVista("CalendarioView");
	}
  
	public void saltarAlerta(LimiteAlertaException e) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("¡Límite de Alerta Superado!");
		alert.setHeaderText("Has superado uno o más límites de gasto");
		alert.setContentText(e.getMessage());
		alert.showAndWait();	
	}
	
	public void mostrarTerminal(){
		cambiarVista("TerminalView");
	}
}

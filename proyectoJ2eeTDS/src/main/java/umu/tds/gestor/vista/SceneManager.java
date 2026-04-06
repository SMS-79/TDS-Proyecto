package umu.tds.gestor.vista;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import umu.tds.gestor.MainAppFX;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;

public class SceneManager {
	
	private Stage stage;
	private Scene escenaActual;
	private BorderPane layoutPrincipal; 
	
	
	public void inicializar(Stage stage) {
		this.stage = stage;
	}
	
	public void showVentanaPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader(MainAppFX.class.getResource("BarraMenu.fxml")); 
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
		cambiarVista("VentanaPrincipal");
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
	
	public File mostrarSelectorFicheroGastos() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar fichero para importar gastos");
		fileChooser.getExtensionFilters().add(
			new FileChooser.ExtensionFilter("Archivos de gastos", "*.csv", "*.txt")
		);

		return fileChooser.showOpenDialog(this.stage);
	}

}

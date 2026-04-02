package umu.tds.gestor.vista;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import umu.tds.gestor.MainAppFX;

public class SceneManager {
	
	private Stage stage;
	private Scene escenaActual;
	
	
	public void inicializar(Stage stage) {
		this.stage = stage;
	}
	
	public void showVentanaPrincipal() {
		cargarYMostrar("PruebaVistaGasto");
	}
	
	private void cargarYMostrar(String fxml){
		try {
			Parent root = loadFXML(fxml);
			if(escenaActual == null) {
				escenaActual = new Scene(root);
				stage.setScene(escenaActual);
		        stage.setTitle("Prueba FXML");
				stage.show();
			}
			else {
				escenaActual.setRoot(root);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainAppFX.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}
	
}

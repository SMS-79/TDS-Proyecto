package umu.tds.gestor;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainAppFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
    	Configuracion configuracion = new ConfiguracionImpl();
    	Configuracion.setInstancia(configuracion);
    	configuracion.getSceneManager().inicializar(primaryStage);
    	
    	configuracion.getSceneManager().showVentanaPrincipal();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}

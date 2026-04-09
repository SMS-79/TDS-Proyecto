package umu.tds.gestor.vista;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import umu.tds.gestor.Configuracion;

public class BarraMenuViewController {

	private static final Logger log = LogManager.getLogger();
	
	@FXML
	private void irAGastos(ActionEvent e) {
		Configuracion.getInstancia().getSceneManager().mostrarTablaGastos();
	}
	
	@FXML
	private void irAddGasto(ActionEvent e) {
		Configuracion.getInstancia().getSceneManager().mostrarAddGasto();
	}

	@FXML
	private void irACategorias() throws IOException {
		Configuracion.getInstancia().getSceneManager().mostrarCreadorCategorias();
	}

	@FXML
	private void irAddAlerta() throws IOException{
		Configuracion.getInstancia().getSceneManager().mostrarAddAlerta();
	}

	@FXML
	public void salir(ActionEvent e) {
		System.exit(0);
	}
	
	@FXML
	void abrirAcerca(ActionEvent e) {
		log.info("Abriendo acerca de...");
	}

}

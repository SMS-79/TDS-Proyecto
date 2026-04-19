package umu.tds.gestor.vista;


import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import umu.tds.gestor.Configuracion;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

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
	private void irACuentas() throws IOException{
		Configuracion.getInstancia().getSceneManager().mostrarTablaCuentas();
	}

	@FXML
	public void salir(ActionEvent e) {
		System.exit(0);
	}
	
	@FXML
	void abrirAcerca(ActionEvent e) {
		log.info("Abriendo acerca de...");
	}
	
	@FXML
	private void irImportarGastos(ActionEvent e) throws LimiteAlertaException {
		File file = Configuracion.getInstancia().getSceneManager().mostrarSelectorFicheroGastos();

		if (file != null) {

			Configuracion.getInstancia().getControladorGestion().importarGastos(file.getAbsolutePath());
			
			// Volvemos a mostrar la tabla para actualizarla
			Configuracion.getInstancia().getSceneManager().mostrarTablaGastos();
		}
	}

}

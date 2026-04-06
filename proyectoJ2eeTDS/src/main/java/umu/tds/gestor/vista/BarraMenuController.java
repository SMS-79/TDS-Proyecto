package umu.tds.gestor.vista;


import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import umu.tds.gestor.Configuracion;

public class BarraMenuController {

	
	@FXML
	private void irAGastos(ActionEvent e) {
		Configuracion.getInstancia().getSceneManager().mostrarTablaGastos();
	}
	
	@FXML
	private void irAddGasto(ActionEvent e) {
		Configuracion.getInstancia().getSceneManager().mostrarAddGasto();
	}

	@FXML
	private void irImportarGastos(ActionEvent e) {
		File file = Configuracion.getInstancia().getSceneManager().mostrarSelectorFicheroGastos();

		if (file != null) {
			// TODO: Esto creo que puede estar violando algún patrón
			Configuracion.getInstancia().getControladorGestion().importarGastosCSV(file.getAbsolutePath());
			
			// Volvemos a mostrar la tabla para actualizarla
			Configuracion.getInstancia().getSceneManager().mostrarTablaGastos();
		}
	}

	@FXML
	private void irAStock() throws IOException {
		System.out.println();
	}

	@FXML
	public void salir(Event e) {
		System.exit(0);
	}
	
	@FXML
	void abrirAcerca(Event e) {
		System.out.println();
	}

}

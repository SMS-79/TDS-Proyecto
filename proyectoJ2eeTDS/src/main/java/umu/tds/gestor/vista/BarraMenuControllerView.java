package umu.tds.gestor.vista;


import java.io.IOException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import umu.tds.gestor.Configuracion;

public class BarraMenuControllerView {

	
	@FXML
	private void irAGastos(ActionEvent e) {
		Configuracion.getInstancia().getSceneManager().mostrarTablaGastos();
	}
	
	@FXML
	private void irAddGasto(ActionEvent e) {
		Configuracion.getInstancia().getSceneManager().mostrarAddGasto();;
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

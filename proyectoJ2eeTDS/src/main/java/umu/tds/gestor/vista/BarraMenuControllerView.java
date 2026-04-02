package umu.tds.gestor.vista;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;

public class BarraMenuControllerView {

	@FXML
	private void irAStock() throws IOException {
		System.out.println();
	}

	@FXML
	public void salir(Event e) {
		Platform.exit();
	}
	
	@FXML
	void abrirAcerca(Event e) {
		System.out.println();
	}

}

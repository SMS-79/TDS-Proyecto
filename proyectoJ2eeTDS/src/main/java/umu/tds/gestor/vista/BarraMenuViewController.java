package umu.tds.gestor.vista;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import umu.tds.gestor.Configuracion;

public class BarraMenuViewController {

	
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
	public void salir(ActionEvent e) {
		System.exit(0);
	}
	
	@FXML
	void abrirAcerca(ActionEvent e) {
		System.out.println();
	}

}

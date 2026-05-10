package umu.tds.gestor.importador;

import umu.tds.gestor.importador.impl.ImportadorGastosCSVImpl;
import umu.tds.gestor.importador.impl.ImportadorGastosTXTImpl;

public class ImportadorGastosFactory {

	public static ImportadorGastos getImportador(String rutaFichero) {
		String rutaMin = rutaFichero.toLowerCase();
		if (rutaMin.endsWith(".csv")) {
			return new ImportadorGastosCSVImpl();
		} else if (rutaMin.endsWith(".txt")) {
			return new ImportadorGastosTXTImpl();
		} else {
			throw new IllegalArgumentException("Formato de fichero no soportado.");
		}
	}
}

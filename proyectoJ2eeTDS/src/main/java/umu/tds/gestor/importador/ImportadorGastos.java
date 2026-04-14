package umu.tds.gestor.importador;

import java.time.LocalDate;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

public abstract class ImportadorGastos {
	
	protected LocalDate fecha;
	protected String cuenta;
	protected String categoria;
	protected String subcategoria;
	protected String nota;
	protected String pagador;
	protected Double cantidad;
	protected String divisa;
	
	protected ImportadorGastos() {}
	
	public abstract void leerFichero(String path) throws LimiteAlertaException;

}

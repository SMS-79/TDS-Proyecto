package umu.tds.gestor.modelo;

import java.time.LocalDate;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

public interface Alerta {
	
	public String getId();

	public void setId(String id);
	
	Categoria getCategoria();
	
	void setCategoria(Categoria categoria);
	
	double getLimite();
	
	void setLimite(double lim);
	
	public double getGastoRealizado();

	public void setGastoRealizado(double gastoRealizado);

	public LocalDate getActivacion();

	public void setActivacion(LocalDate activacion);
	
	void reiniciar();
	
	void añadirGastoAlerta(Gasto g) throws LimiteAlertaException;

}
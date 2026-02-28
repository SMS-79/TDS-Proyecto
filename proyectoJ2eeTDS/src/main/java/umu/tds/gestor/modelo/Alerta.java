package umu.tds.gestor.modelo;

import java.time.LocalDate;
import java.util.Optional;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.Intervalo;

public interface Alerta {
	
	public String getId();

	public void setId(String id);
	
	Categoria getCategoria();
	
	void setCategoria(Categoria categoria);
	
	Intervalo getIntervalo();
	
	void setIntervalo(Intervalo inter);
	
	double getLimite();
	
	void setLimite(double lim);
	
	public double getGastoRealizado();

	public void setGastoRealizado(double gastoRealizado);

	public LocalDate getActivacion();

	public void setActivacion(LocalDate activacion);
	
	void reiniciar();
	
	void añadirGastoAlerta(Gasto g) throws LimiteAlertaException;

}
package umu.tds.gestor.modelo;

import java.time.LocalDate;
import java.util.Optional;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.impl.Intervalo;

public interface Alerta {
	
	public String getId();

	public void setId(String id);
	
	CategoriaImpl getCategoria();
	
	void setCategoria(CategoriaImpl categoria);
	
	public Intervalo getIntervalo();
	
	double getLimite();
	
	void setLimite(double lim);
	
	public double getGastoRealizado();

	public LocalDate getActivacion();
	
	void reiniciar();
	
	void añadirGastoAlerta(GastoImpl g) throws LimiteAlertaException;

}
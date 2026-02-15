package umu.tds.gestor.modelo;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

public interface Alerta {
	
	Categoria getCategoria();
	
	void setCategoria(Categoria categoria);
	
	double getLimite();
	
	void setLimite(double lim);
	
	void reiniciar();
	
	void añadirGastoAlerta(Gasto g) throws LimiteAlertaException;

}
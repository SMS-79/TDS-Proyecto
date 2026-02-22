package umu.tds.gestor.modelo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import umu.tds.gestor.modelo.impl.GastoImpl;

public interface CuentaGasto {
	
	List<String> getParticipantes();
	
	Map<String, Double> getDistribuciones();
	
	Gasto getGasto(String nombre);
	
	void setGasto(String pagador, Gasto gasto);
	
	void setDistribucion(String nombre, double value);
	
	void setDistribuciones(Map<String, Double> distribuciones);
	
	void introducirGasto(String nombre, GastoImpl gasto);
	
	void recalcularSaldos();
	
	UUID getId();
	
}

package umu.tds.gestor.modelo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CuentaGasto {
	
	List<String> getParticipantes();
	
	Map<String, Double> getDistribuciones();
	
	Gasto getGasto(String nombre);
	
	void setGasto(String pagador, Gasto gasto);
	
	void setDistribucion(String nombre, double value);
	
	void setDistribuciones(Map<String, Double> distribuciones);
	
	void recalcularSaldos();
	
	UUID getId();
	
}

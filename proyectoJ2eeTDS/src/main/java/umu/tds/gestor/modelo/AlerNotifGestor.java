package umu.tds.gestor.modelo;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.modelo.impl.Notificacion;

public interface AlerNotifGestor {
	
	Alerta crearAlerta(Categoria cat, double lim, Intervalo tipo);
	
	void anadirGastoAlerta(Alerta a, Gasto g) throws LimiteAlertaException;
	
	void quitarGastoAlerta(Alerta a, Gasto g);
	
	Notificacion crearNotificacion(String mensaje);
	
}

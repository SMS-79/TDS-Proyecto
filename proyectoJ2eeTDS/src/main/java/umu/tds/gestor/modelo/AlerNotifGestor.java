package umu.tds.gestor.modelo;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.modelo.impl.Notificacion;

public interface AlerNotifGestor {
	
	Alerta crearAlerta(Categoria cat, double lim, Intervalo tipo);
	
	void añadirGastoAlerta(Alerta a, GastoImpl g) throws LimiteAlertaException;
	
	void quitarGastoAlerta(Alerta a, GastoImpl g);
	
	Notificacion crearNotificacion(String mensaje);
	
}

package umu.tds.gestor.modelo;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.AlertaMensual;
import umu.tds.gestor.modelo.impl.AlertaSemanal;
import umu.tds.gestor.modelo.impl.Intervalo;

public interface AlerNotifGestor {
	
	Alerta crearAlerta(Categoria cat, double lim, Intervalo tipo);
	
	void gastoAlerta(Alerta a, Gasto g) throws LimiteAlertaException;
	
	Notificacion crearNotificacion(String mensaje);
	
}

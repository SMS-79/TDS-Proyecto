package umu.tds.gestor.modelo;

import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.modelo.impl.AlertaMensual;
import umu.tds.gestor.modelo.impl.AlertaSemanal;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.modelo.impl.Intervalo;

public interface AlerNotifGestor {
	
	Alerta crearAlerta(CategoriaImpl cat, double lim, Intervalo tipo);
	
	void añadirGastoAlerta(Alerta a, GastoImpl g) throws LimiteAlertaException;
	
	void quitarGastoAlerta(Alerta a, GastoImpl g);
	
	Notificacion crearNotificacion(String mensaje);
	
}

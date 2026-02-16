package umu.tds.gestor.modelo.impl;

import java.util.Optional;

import umu.tds.gestor.modelo.AlerNotifGestor;
import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.Notificacion;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

public class AlerNotifGestorImpl implements AlerNotifGestor {

	@Override
	public Alerta crearAlerta(double lim, Intervalo tipo) {
		Alerta a;
		switch(tipo) {
		case SEMANAL: 
			a = new AlertaSemanal(lim);
			break;
		case MENSUAL:
			a = new AlertaMensual(lim);
			break;
		default:
			a = new AlertaSemanal(lim);
			break;
		}
		
		return a;
	}
	

	@Override
	public void gastoAlerta(Alerta a, Gasto g) throws LimiteAlertaException {
		a.añadirGastoAlerta(g);
			
	}

	@Override
	public Notificacion crearNotificacion(String mensaje) {
		return new NotificacionImpl(mensaje);
	}

}

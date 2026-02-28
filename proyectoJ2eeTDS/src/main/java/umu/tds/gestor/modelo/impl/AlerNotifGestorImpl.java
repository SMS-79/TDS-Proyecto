package umu.tds.gestor.modelo.impl;

import java.util.Optional;

import umu.tds.gestor.modelo.AlerNotifGestor;
import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Categoria;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.Notificacion;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;
import umu.tds.gestor.repository.impl.RepositorioAlertasImpl;

public class AlerNotifGestorImpl implements AlerNotifGestor {
	
	private static AlerNotifGestorImpl instancia = null;
	
	public static AlerNotifGestorImpl getInstancia() {
		if(instancia == null) {
			instancia = new AlerNotifGestorImpl();
		}
		return instancia;
	}

	@Override
	public Alerta crearAlerta(Categoria cat, double lim, Intervalo tipo) {
		Alerta a;
		switch(tipo) {
		case SEMANAL: 
			a = new AlertaSemanal(cat, lim);
			break;
		case MENSUAL:
			a = new AlertaMensual(cat, lim);
			break;
		default:
			a = new AlertaSemanal(cat, lim);
			break;
		}
		
		return a;
	}
	
	public Alerta crearAlerta(double lim, Intervalo tipo) {
		return crearAlerta(null, lim, tipo);
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

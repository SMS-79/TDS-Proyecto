package umu.tds.gestor.modelo.impl;

import umu.tds.gestor.modelo.AlerNotifGestor;
import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

public class AlerNotifGestorImpl implements AlerNotifGestor {
	
	// Patron Singleton
	private static AlerNotifGestorImpl instancia = null;
	
	public static AlerNotifGestorImpl getInstancia() {
		if(instancia == null) {
			instancia = new AlerNotifGestorImpl();
		}
		return instancia;
	}

	
	// Método factoría para crear alertas segun el tipo
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
	
	// Crear alertas sin necesidad de incluir categoría
	public Alerta crearAlerta(double lim, Intervalo tipo) {
		return crearAlerta(null, lim, tipo);
	}
	
	//Función para añadir gastos a las alertas y comprobar que no exceda el límite
	@Override
	public void anadirGastoAlerta(Alerta a, Gasto g) throws LimiteAlertaException {
		a.anadirGastoAlerta(g);		
	}
	
	@Override
	public void quitarGastoAlerta(Alerta a, Gasto g){
		a.quitarGastoAlerta(g);		
	}

	// Función para crear Notificaciones
	@Override
	public Notificacion crearNotificacion(String mensaje) {
		return new Notificacion(mensaje);
	}

}

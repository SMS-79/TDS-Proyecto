package umu.tds.gestor.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.modelo.impl.NotificacionImpl;

public class RepositorioNotificacionesImpl {

private static RepositorioNotificacionesImpl instancia = null;
	
	public static RepositorioNotificacionesImpl getInstancia() {
		if(instancia == null) {
			instancia = new RepositorioNotificacionesImpl();
		}
		return instancia;
	}
	
	private List<NotificacionImpl> notificaciones;
	
	public List<? extends NotificacionImpl> getNotificaciones(){
		return Collections.unmodifiableList(notificaciones);
	}
	
	public NotificacionImpl filtrarNotificacion(String id) {
		
		
	}
	
	public void añadirNotificacion(NotificacionImpl notif){
		notificaciones.add(notif);
	}
	
	
}

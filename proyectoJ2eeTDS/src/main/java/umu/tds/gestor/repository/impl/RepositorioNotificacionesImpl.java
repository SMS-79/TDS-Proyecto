package umu.tds.gestor.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.modelo.impl.NotificacionImpl;
import umu.tds.gestor.repository.RepositorioNotificaciones;

public class RepositorioNotificacionesImpl implements RepositorioNotificaciones {

private static RepositorioNotificacionesImpl instancia = null;
	
	public static RepositorioNotificacionesImpl getInstancia() {
		if(instancia == null) {
			instancia = new RepositorioNotificacionesImpl();
		}
		return instancia;
	}
	
	private List<NotificacionImpl> notificaciones;
	
	@Override
	public List<? extends NotificacionImpl> getNotificaciones(){
		return Collections.unmodifiableList(notificaciones);
	}
	
	@Override
	public NotificacionImpl filtrarNotificacion(String id) {
		return notificaciones.stream()
				.filter(n -> n.getId().equals(id))
				.findFirst()
				.orElse(null);
		
	}
	
	@Override
	public void añadirNotificacion(NotificacionImpl notif){
		notificaciones.add(notif);
	}
	
	
}

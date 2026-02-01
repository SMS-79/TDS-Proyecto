package umu.tds.gestor.repository;

import java.util.List;

import umu.tds.gestor.modelo.impl.NotificacionImpl;

public interface RepositorioNotificaciones {

	List<? extends NotificacionImpl> getNotificaciones();

	NotificacionImpl filtrarNotificacion(String id);
	
	NotificacionImpl crearNotificacion(String mensaje);
	
}

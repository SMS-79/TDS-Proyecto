package umu.tds.gestor.repository;

import java.util.List;

import umu.tds.gestor.modelo.impl.NotificacionImpl;

public interface RepositorioNotificaciones {

	List<? extends NotificacionImpl> getNotificaciones();

	NotificacionImpl filtrarNotificacion(String id);
	
	void añadirNotificacion(String mensaje);
	
}

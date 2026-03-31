package umu.tds.gestor.repository;

import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.impl.NotificacionImpl;

public interface RepositorioNotificaciones {

	List<? extends NotificacionImpl> getNotificaciones();

	NotificacionImpl filtrarNotificacion(UUID id);
	
	void añadirNotificacion(NotificacionImpl notif);
	
}

package umu.tds.gestor.repository;

import java.util.List;
import java.util.UUID;

import umu.tds.gestor.modelo.impl.Notificacion;

public interface RepositorioNotificaciones {

	List<? extends Notificacion> getNotificaciones();

	Notificacion filtrarNotificacion(UUID id);
	
	void anadirNotificacion(Notificacion notif);
	
}

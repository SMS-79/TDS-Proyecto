package umu.tds.gestor.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import umu.tds.gestor.modelo.impl.Notificacion;
import umu.tds.gestor.repository.RepositorioNotificaciones;

public class RepositorioNotificacionesImpl implements RepositorioNotificaciones {

	private static RepositorioNotificacionesImpl instancia = null;

	private BaseDeDatosImpl BD;
	
	public static RepositorioNotificacionesImpl getInstancia() {
		if(instancia == null) {
			instancia = new RepositorioNotificacionesImpl();
		}
		return instancia;
	}
	
	private List<Notificacion> notificaciones;
	
	public RepositorioNotificacionesImpl() {
		if(BaseDeDatosImpl.getBD() != null) {
			this.BD = BaseDeDatosImpl.getBD();
		}
		
		if(BD.getAlertas() != null) {
			this.notificaciones = (List<Notificacion>) BD.getNotificaciones();
		}
		else {
			this.notificaciones = new ArrayList<Notificacion>();
		}
	}
	
	@Override
	public List<? extends Notificacion> getNotificaciones(){
		return Collections.unmodifiableList(notificaciones);
	}
	
	@Override
	public Notificacion filtrarNotificacion(UUID id) {
		return notificaciones.stream()
				.filter(n -> n.getId().equals(id))
				.findFirst()
				.orElse(null);
		
	}
	
	@Override
	public void anadirNotificacion(Notificacion notif){
		notificaciones.add(notif);
		BD.guardarFichero();
	}
	
	
}

package umu.tds.gestor.modelo.impl;

import java.util.Random;
import java.util.UUID;

import umu.tds.gestor.modelo.Notificacion;

public class NotificacionImpl implements Notificacion {
	
	private String id;
	private String mensaje;
	
	public NotificacionImpl(String m) {
		this.id = UUID.randomUUID().toString();
		this.mensaje = m;
	}
	
	public String getId() { return this.id; }
	
	public String getMensaje() { return this.mensaje; }
	
	
	
}

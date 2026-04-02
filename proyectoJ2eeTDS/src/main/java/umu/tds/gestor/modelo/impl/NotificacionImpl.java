package umu.tds.gestor.modelo.impl;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import umu.tds.gestor.modelo.Notificacion;


@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_notif"
)

public class NotificacionImpl implements Notificacion {
	
	@JsonProperty("id_notif")
	private UUID id = UUID.randomUUID();
	
	private String mensaje;
	
	public NotificacionImpl() {	}
	
	public NotificacionImpl(String m) {
		this.mensaje = m;
	}

	public UUID getId() {
		return id;
	}


	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
	
	
}

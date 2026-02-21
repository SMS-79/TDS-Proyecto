package umu.tds.gestor.modelo.impl;

import java.util.Objects;

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
	private String id;
	
	private String mensaje;
	
	public NotificacionImpl() {	}
	
	public NotificacionImpl(String m) {
		this.mensaje = m;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
	
	
}

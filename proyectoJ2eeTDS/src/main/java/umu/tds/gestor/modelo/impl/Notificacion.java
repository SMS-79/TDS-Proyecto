package umu.tds.gestor.modelo.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_notif"
)

public class Notificacion {
	
	@JsonProperty("id_notif")
	private UUID id = UUID.randomUUID();
	
	private String mensaje;
	
	private LocalDateTime fechaCreacion;
	
	public Notificacion() {	}
	
	public Notificacion(String m) {
		this.mensaje = m;
		this.fechaCreacion = LocalDateTime.now();
	}

	public UUID getId() {
		return this.id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public LocalDateTime getFechaCreacion() {		
		return this.fechaCreacion;
	}
	
	
	
	
	
}

package umu.tds.gestor.modelo.impl;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Categoria;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_alert"
)

public class AlertaMensual implements Alerta {
	
	@JsonProperty("id_alert")
	private String id;
	
	private Categoria categoria;
	private double limite;
	private double gastoRealizado;
	private LocalDate activacion;
	
	public AlertaMensual() {}
	
	public AlertaMensual(double lim) {
		this(null, lim);
	}
	
	public AlertaMensual(Categoria c, double lim) {
		this.categoria = c;
		this.limite = lim;
		this.activacion = LocalDate.now();
	}
	
	
	

	
	//Getters y setters
	
	@Override
	public String getId() { return id;	}
	@Override
	public void setId(String id) { this.id = id;	}
	@Override
	public Categoria getCategoria() { return this.categoria;	}
	@Override
	public void setCategoria(Categoria categ) {	this.categoria = categ;	}
	@Override
	public double getLimite() { return this.limite;	}
	@Override
	public void setLimite(double lim) {	this.limite = lim;	}
	@Override
	public double getGastoRealizado() { return gastoRealizado;	}
	@Override
	public void setGastoRealizado(double gastoRealizado) { this.gastoRealizado = gastoRealizado;	}
	@Override
	public LocalDate getActivacion() { return activacion;	}
	@Override
	public void setActivacion(LocalDate activacion) { this.activacion = activacion;		}
	
	
	
	
	
	
	
	
	// Reinicia el temporizador de la alerta
	@Override
	public void reiniciar() {
		this.activacion = LocalDate.now();
		this.gastoRealizado = 0;
	}
	
	@Override
	public void añadirGastoAlerta(Gasto g) throws LimiteAlertaException{
		
		// Si ha pasado una semana se reinicia el contador del gasto total
		if(!activacion.plusMonths(1).isAfter(LocalDate.now())) {
			reiniciar();
		}
		
		// Comprobación de categoría
		if(Objects.isNull(this.categoria) || this.categoria.equals(g.getCategoria())) {
			gastoRealizado += g.getCantidad();
		}
		
		// Comprobación de gasto limite y generacion de alerta para creacion de Notificacion
		if(gastoRealizado >= limite) {
			String mensaje = "Limite mensual de " + limite +  "€ superado. " + gastoRealizado + "€ gastados en total";
			if(Objects.nonNull(this.categoria)) {
				mensaje += " en la catgoría " + this.categoria.getNombre(); 
			}	
			mensaje += '.';
			throw new LimiteAlertaException(mensaje);
		}
	}
	
	
}

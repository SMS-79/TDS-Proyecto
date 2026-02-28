package umu.tds.gestor.modelo.impl;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

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
	
	private Optional<CategoriaImpl> categoria;
	private final Intervalo intervalo = Intervalo.MENSUAL;
	private double limite;
	private double gastoRealizado;
	private LocalDate activacion;
	
	// Constructor vacío para la persistencia
	public AlertaMensual() {}
	
	// Constructor sin categoria
	public AlertaMensual(double lim) {
		this(null, lim);
	}
	
	// Constructor con categoría
	public AlertaMensual(CategoriaImpl c, double lim) {
		this.categoria = Optional.ofNullable(c);
		this.limite = lim;
		this.activacion = LocalDate.now();
	}
	

	
	//Getters y setters
	
	@Override
	public String getId() { return id;	}
	@Override
	public void setId(String id) { this.id = id;	}
	@Override
	public CategoriaImpl getCategoria() { return this.categoria.orElse(null);	}
	@Override
	public void setCategoria(CategoriaImpl categ) {	this.categoria = Optional.of(categ);	}
	@Override
	public Intervalo getIntervalo() { return this.intervalo; }
	@Override
	public double getLimite() { return this.limite;	}
	@Override
	public void setLimite(double lim) {	this.limite = lim;	}
	@Override
	public double getGastoRealizado() { return gastoRealizado;	}
	@Override
	public LocalDate getActivacion() { return activacion;	}
	
	
	
	
	
	
	
	
	// Reinicia el temporizador de la alerta
	@Override
	public void reiniciar() {
		this.activacion = LocalDate.now();
		this.gastoRealizado = 0;
	}
	
	@Override
	public void añadirGastoAlerta(GastoImpl g) throws LimiteAlertaException{
		
		// Si ha pasado una semana se reinicia el contador del gasto total
		if(!activacion.plusMonths(1).isAfter(LocalDate.now())) {
			reiniciar();
		}
		
		// Comprobación de categoría
		if(this.categoria.isEmpty() || this.categoria.orElse(null).equals(g.getCategoria())) {
			gastoRealizado += g.getCantidad();
		}
		
		// Comprobación de gasto limite y generacion de alerta para creacion de Notificacion
		if(gastoRealizado >= limite) {
			String mensaje = "Limite mensual de " + limite +  "€ superado. " + gastoRealizado + "€ gastados en total";
			if(this.categoria.isPresent()) {
				mensaje += " en la categoría " + this.categoria.get().getNombre(); 
			}	
			mensaje += '.';
			throw new LimiteAlertaException(mensaje);
		}
	}
	
	
}

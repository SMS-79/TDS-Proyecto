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

public class AlertaMensual extends Alerta {
	
	@JsonProperty("id_alert")
	private String id;
	
	private final Intervalo intervalo = Intervalo.MENSUAL;
	
	// Constructor vacío para la persistencia
	public AlertaMensual() { super(); }
	
	// Constructor sin categoria
	public AlertaMensual(double lim) {
		this(null, lim);
	}
	
	// Constructor con categoría
	public AlertaMensual(CategoriaImpl c, double lim) {
		super(c, lim);
	}
	

	
	
	@Override
	public Intervalo getIntervalo() { return this.intervalo; }
	
	
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
	
	@Override
	public void quitarGastoAlerta(GastoImpl g){
		
		// Si ha pasado una semana se reinicia el contador del gasto total
		if(!activacion.plusMonths(1).isAfter(LocalDate.now())) {
			reiniciar();
		}
		
		// Comprobación de categoría
		if(this.categoria.isEmpty() || this.categoria.orElse(null).equals(g.getCategoria())) {
			gastoRealizado += g.getCantidad();
		}
		
	}
	
	
}

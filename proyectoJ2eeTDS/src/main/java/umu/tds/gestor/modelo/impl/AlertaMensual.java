package umu.tds.gestor.modelo.impl;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_alert"
)

public class AlertaMensual extends Alerta {
	
	private final Intervalo intervalo = Intervalo.MENSUAL;
	
	// Constructor vacío para la persistencia
	public AlertaMensual() { super(); }
	
	// Constructor sin categoria
	public AlertaMensual(double lim) {
		this(null, lim);
	}
	
	// Constructor con categoría
	public AlertaMensual(Categoria c, double lim) {
		super(c, lim);
	}
	
	@Override
	public Intervalo getIntervalo() { return this.intervalo; }
	
	
	@Override
	public void anadirGastoAlerta(GastoImpl g) throws LimiteAlertaException{
		
		// Si ha pasado una semana se reinicia el contador del gasto total
		if(!activacion.plusMonths(1).isAfter(LocalDate.now())) {
			reiniciar();
		}

		boolean isMesActual = g.getFecha().getMonth() == LocalDate.now().getMonth();
		boolean isAnoActual = g.getFecha().getYear() == LocalDate.now().getYear();

		if(isAnoActual && isMesActual){
			// Comprobación de categoría
			if(this.categoria.isEmpty() || this.categoria.orElse(null).equals(g.getCategoria())) {
				gastoRealizado += g.getCantidad();
				// Comprobación de gasto limite y generacion de alerta para creacion de Notificacion
				if(gastoRealizado >= limite) {
					String mensaje = "Limite mensual de " + limite +  "€ superado. " + gastoRealizado + "€ gastados en total";
					if(this.categoria.isPresent()) {
						mensaje += " en la categoría " + this.categoria.get().toString(); 
					}	
					mensaje += '.';
					throw new LimiteAlertaException(mensaje);
				}
			}
		}
	}
	
	@Override
	public void quitarGastoAlerta(GastoImpl g){
		
		// Si ha pasado una semana se reinicia el contador del gasto total
		if(!activacion.plusMonths(1).isAfter(LocalDate.now())) {
			reiniciar();
		}
	
		boolean isMesActual = g.getFecha().getMonth() == LocalDate.now().getMonth();
		boolean isAnoActual = g.getFecha().getYear() == LocalDate.now().getYear();

		if(isAnoActual && isMesActual){			 
			// Comprobación de categoría
			if(this.categoria.isEmpty() || this.categoria.orElse(null).equals(g.getCategoria())) {
				gastoRealizado -= g.getCantidad();
				if(gastoRealizado < 0){
					gastoRealizado = 0; 
				}
			}
		}
	}
	
	
}

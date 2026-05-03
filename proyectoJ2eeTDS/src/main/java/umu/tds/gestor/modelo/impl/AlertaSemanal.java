package umu.tds.gestor.modelo.impl;

import java.time.LocalDate;
import java.time.temporal.IsoFields;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_alert"
)

public class AlertaSemanal extends Alerta {
	
	private final Intervalo intervalo = Intervalo.SEMANAL;
	
	// Constructor vacío para la persistencia
	public AlertaSemanal() { super(); }
	
	// Constructor sin categoria
	public AlertaSemanal(double lim) {
		this(null, lim);
	}
	
	// Constructor con categoría
	public AlertaSemanal(Categoria c,  double lim) {
		super(c, lim);
	}
	
	@Override
	public Intervalo getIntervalo() { return this.intervalo; }
	
	@Override
	public void anadirGastoAlerta(GastoImpl g) throws LimiteAlertaException{
		
		// Si ha pasado una semana se reinicia el contador del gasto total
		if(!activacion.plusWeeks(1).isAfter(LocalDate.now())) {
			reiniciar();
		}

		int semanaGasto = g.getFecha().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		int anoGasto = g.getFecha().get(IsoFields.WEEK_BASED_YEAR);

		int semanaActual =LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		int anoActual = LocalDate.now().get(IsoFields.WEEK_BASED_YEAR);

		if(semanaGasto == semanaActual && anoGasto == anoActual){	
			// Comprobación de categoría
			if(this.categoria.isEmpty() || this.categoria.orElse(null).equals(g.getCategoria())) {
				gastoRealizado += g.getCantidad();
			// Comprobación de gasto limite y generacion de alerta para creacion de Notificacion
				if(gastoRealizado >= limite) {
					String mensaje = "Limite semanal de " + limite +  "€ superado. " + gastoRealizado + "€ gastados en total";
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
		if(!activacion.plusWeeks(1).isAfter(LocalDate.now())) {
			reiniciar();
		}

		int semanaGasto = g.getFecha().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		int anoGasto = g.getFecha().get(IsoFields.WEEK_BASED_YEAR);

		int semanaActual =LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		int anoActual = LocalDate.now().get(IsoFields.WEEK_BASED_YEAR);
		
		if(semanaGasto == semanaActual && anoGasto == anoActual){
			// Comprobación de categoría
			if(this.categoria.isEmpty() || this.categoria.orElse(null).equals(g.getCategoria())) {
				gastoRealizado -= g.getCantidad();
				if(gastoRealizado < 0) {
					gastoRealizado = 0;
				}
			}
		}
	}


}

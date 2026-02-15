package umu.tds.gestor.modelo.impl;

import java.time.LocalDate;
import java.util.Objects;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Categoria;
import umu.tds.gestor.modelo.Gasto;
import umu.tds.gestor.modelo.exceptions.LimiteAlertaException;

public class AlertaSemanal implements Alerta {

	private Categoria categoria;
	private double limite;
	private double gastoRealizado;
	private LocalDate activacion;
	
	public AlertaSemanal(double lim) {
		this(null, lim);
	}
	
	public AlertaSemanal(Categoria c,  double lim) {
		this.categoria = c;
		this.limite = lim;
		this.activacion = LocalDate.now();
	}
	
	
	
	public Categoria getCategoria() { return this.categoria;	}
	
	public void setCategoria(Categoria categ) {	this.categoria = categ;	}
	
	public double getLimite() { return this.limite;	}

	public void setLimite(double lim) {	this.limite = lim;	}
	
	
	
	public void reiniciar() {
		this.activacion = LocalDate.now();
		this.gastoRealizado = 0;
	}
	
	public void añadirGastoAlerta(Gasto g) throws LimiteAlertaException{
		
		// Si ha pasado una semana se reinicia el contador del gasto total
		if(!activacion.plusWeeks(1).isAfter(LocalDate.now())) {
			reiniciar();
		}
		
		// Comprobación de categoría
		if(Objects.isNull(this.categoria) || this.categoria.equals(g.getCategoria())) {
			gastoRealizado += g.getCantidad();
		}
		
		// Comprobación de gasto limite y generacion de alerta para creacion de Notificacion
		if(gastoRealizado >= limite) {
			String mensaje = "Limite semanal de " + limite +  "superado. " + gastoRealizado + "€ gastados en total";
			if(Objects.nonNull(this.categoria)) {
				mensaje += " en la catgoría " + this.categoria.getNombre(); 
			}	
			
			mensaje += '.';
			throw new LimiteAlertaException(mensaje);
		}
	}


}

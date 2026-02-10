package umu.tds.gestor.modelo.impl;

import java.time.LocalDate;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Categoria;
import umu.tds.gestor.modelo.Intervalo;

public class AlertaMensual implements Alerta {
	private Categoria categoria;
	private double limite;
	private double gastoRealizado;
	private LocalDate activacion;
	
	public AlertaMensual(double lim) {
		this(null, lim);
	}
	
	public AlertaMensual(Categoria c, double lim) {
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
	
	public void añadirGastoAlerta(double g) {
		gastoRealizado += g;
		if(gastoRealizado >= limite) {
			generarNotificacion();
		}
	}
	
	public void generarNotificacion() {
		
		
	}
}

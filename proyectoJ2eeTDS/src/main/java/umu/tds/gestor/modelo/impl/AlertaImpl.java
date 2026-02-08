package umu.tds.gestor.modelo.impl;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Categoria;
import umu.tds.gestor.modelo.Intervalo;

public class AlertaImpl implements Alerta {

	private Categoria categoria;
	private Intervalo intervalo;
	private double limite;
	
	public AlertaImpl() {
		
	}
	
	public Categoria getCategoria() { 
		return this.categoria;
	}
	
	public Intervalo getIntervalo() { 
		return this.intervalo;
	}
	
	public double getLimite() { 
		return this.limite;
	}

	public void setCategoria(Categoria categ) {
		this.categoria = categ;
	}

	public void setIntervalo(Intervalo interv) {
		this.intervalo = interv;
		
	}

	public void setLimite(double lim) {
		this.limite = lim;		
	}
}

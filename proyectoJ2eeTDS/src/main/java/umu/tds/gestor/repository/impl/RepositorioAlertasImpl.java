package umu.tds.gestor.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.repository.RepositorioAlertas;

public class RepositorioAlertasImpl implements RepositorioAlertas {

	private static RepositorioAlertasImpl instancia = null;
	
	public static RepositorioAlertasImpl getInstancia() {
		if(instancia == null) {
			instancia = new RepositorioAlertasImpl();
		}
		return instancia;
	}
	
	private List<Alerta> alertas;
	
	public List<? extends Alerta> getAlertas(){
		return Collections.unmodifiableList(alertas);
	}
	
	public Alerta filtrarAlerta(Optional<CategoriaImpl> categoria, Intervalo interv, double limite){
		
		
	}
	
	public void añadirAlerta(Alerta alerta) {
		alertas.add(alerta);
	}
	
	public void borrarAlerta(Alerta alerta) {
		alertas.remove(alerta);
	}
	
}

package umu.tds.gestor.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Categoria;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.repository.RepositorioAlertas;

public class RepositorioAlertasImpl implements RepositorioAlertas {

	// Patron Singleton
	private static RepositorioAlertasImpl instancia = null;
	
	public static RepositorioAlertasImpl getInstancia() {
		if(instancia == null) {
			instancia = new RepositorioAlertasImpl();
		}
		return instancia;
	}
	
	private List<Alerta> alertas;
	
	@Override
	public List<? extends Alerta> getAlertas(){
		return Collections.unmodifiableList(alertas);
	}
	
	
	@Override
	public List<? extends Alerta> filtrarAlerta(Optional<Categoria> categoria, Intervalo interv, double limite){
		return alertas.stream()
			.filter(a -> a.getLimite() == limite)
			.filter(a -> a.getIntervalo().equals(interv))
			.filter(a -> a.getCategoria().equals(categoria.orElse(null)))
			.toList();
	}
	
	@Override
	public void añadirAlerta(Alerta alerta) {
		alertas.add(alerta);
	}
	
	@Override
	public void borrarAlerta(Alerta alerta) {
		alertas.remove(alerta);
	}

	
}

package umu.tds.gestor.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Categoria;
import umu.tds.gestor.modelo.impl.AlertaSemanal;
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
	
	@Override
	public List<? extends Alerta> filtrarAlerta(Optional<Categoria> categoria, Intervalo interv, double limite){
	
		return alertas.stream()
			.filter(a -> a.getLimite() == limite && a.getIntervalo().equals(interv) && a.getCategoria().equals(categoria.orElse(null)))
			.collect(Collectors.toList());
				
	}
	
	public void añadirAlerta(Alerta alerta) {
		alertas.add(alerta);
	}
	
	public void borrarAlerta(Alerta alerta) {
		alertas.remove(alerta);
	}

	
}

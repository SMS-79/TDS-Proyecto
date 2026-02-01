package umu.tds.gestor.repository;

import java.util.List;

import umu.tds.gestor.modelo.Intervalo;
import umu.tds.gestor.modelo.impl.AlertaImpl;
import umu.tds.gestor.modelo.impl.CategoriaImpl;

public interface RepositorioAlertas {
	
	List<? extends AlertaImpl> getAlertas();
	
	AlertaImpl filtrarAlerta(CategoriaImpl categoria, Intervalo interv, double limite);
	
	void createAlerta(CategoriaImpl categoria, Intervalo interv, double limite);
	
	void borrarAlerta(AlertaImpl alerta);
	
	

}

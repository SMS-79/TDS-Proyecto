package umu.tds.gestor.repository;

import java.util.List;

import umu.tds.gestor.modelo.Intervalo;
import umu.tds.gestor.modelo.impl.AlertaSemanal;
import umu.tds.gestor.modelo.impl.CategoriaImpl;

public interface RepositorioAlertas {
	
	List<? extends AlertaSemanal> getAlertas();
	
	AlertaSemanal filtrarAlerta(CategoriaImpl categoria, Intervalo interv, double limite);
	
	void createAlerta(CategoriaImpl categoria, Intervalo interv, double limite);
	
	void borrarAlerta(AlertaSemanal alerta);
	
	

}

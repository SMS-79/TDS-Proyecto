package umu.tds.gestor.repository;

import java.util.List;
import java.util.Optional;

import umu.tds.gestor.modelo.Alerta;
import umu.tds.gestor.modelo.Categoria;
import umu.tds.gestor.modelo.impl.Intervalo;
import umu.tds.gestor.modelo.impl.AlertaSemanal;
import umu.tds.gestor.modelo.impl.CategoriaImpl;

public interface RepositorioAlertas {
	
	List<? extends Alerta> getAlertas();
	
	List<? extends Alerta> filtrarAlerta(Optional<Categoria> categoria, Intervalo interv, double limite);
	
	void añadirAlerta(Alerta alerta);
	
	void borrarAlerta(Alerta alerta);
	
	

}

package umu.tds.gestor.repository;

import umu.tds.gestor.repository.impl.BaseDeDatosImpl;

public interface BaseDeDatos {
	
	void guardarFichero();
	
	void cargarFichero();
	
	void setBD(BaseDeDatosImpl bd);
	
}

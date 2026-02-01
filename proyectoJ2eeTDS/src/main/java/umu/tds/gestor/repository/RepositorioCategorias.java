package umu.tds.gestor.repository;

import java.util.List;

import umu.tds.gestor.modelo.impl.CategoriaImpl;

public interface RepositorioCategorias {

	List<? extends CategoriaImpl> getCategorias();
	
	CategoriaImpl filtrarCategoria(String categoriaId);
	
	void crearCategoria(String categoriaId);
	
	
	
}

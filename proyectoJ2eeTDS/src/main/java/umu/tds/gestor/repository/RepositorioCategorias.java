package umu.tds.gestor.repository;

import java.util.List;
import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;

public interface RepositorioCategorias {

	List<? extends CategoriaImpl> getCategorias();
	
	List<? extends CategoriaImpl> filtrarCategorias(String categoriaId);
	
	void aniadirCategoria(CategoriaImpl categoria);
	
}

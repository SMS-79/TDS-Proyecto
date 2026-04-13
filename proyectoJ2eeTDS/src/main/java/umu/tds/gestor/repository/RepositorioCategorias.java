package umu.tds.gestor.repository;

import java.util.List;
import umu.tds.gestor.modelo.impl.Categoria;
import umu.tds.gestor.modelo.impl.GastoImpl;

public interface RepositorioCategorias {

	List<? extends Categoria> getCategorias();
	
	List<? extends Categoria> filtrarCategorias(String categoriaId);
	
	void aniadirCategoria(Categoria categoria);
	
}

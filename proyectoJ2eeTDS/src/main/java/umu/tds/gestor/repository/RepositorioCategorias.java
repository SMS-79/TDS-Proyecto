package umu.tds.gestor.repository;

import java.util.List;
import umu.tds.gestor.modelo.impl.Categoria;

public interface RepositorioCategorias {

	List<? extends Categoria> getCategorias();
	
	List<? extends Categoria> filtrarCategorias(String categoriaId);
	
	void anadirCategoria(Categoria categoria);
	
}

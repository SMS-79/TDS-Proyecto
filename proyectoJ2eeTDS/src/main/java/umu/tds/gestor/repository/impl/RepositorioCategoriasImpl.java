
package umu.tds.gestor.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.repository.RepositorioCategorias;

public class RepositorioCategoriasImpl implements RepositorioCategorias {

	
	private static RepositorioCategoriasImpl instancia = null;
	
	public static RepositorioCategoriasImpl getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioCategoriasImpl();
		}
		
		return instancia; 
	}
	
	private List<CategoriaImpl> categorias = new ArrayList<CategoriaImpl>(); 
	
	@Override
	public List<? extends CategoriaImpl> getCategorias() {
		return Collections.unmodifiableList(categorias);
	}

	@Override
	public List<? extends CategoriaImpl> filtrarCategorias(String categoriaId) {
		return categorias.stream()
				.filter(c -> c.getNombre().startsWith(categoriaId))
				.toList();
	}

	@Override
	public void aniadirCategoria(CategoriaImpl categoria) {
		categorias.add(categoria);	
	}

}
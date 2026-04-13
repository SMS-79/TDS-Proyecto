
package umu.tds.gestor.repository.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;
import umu.tds.gestor.repository.RepositorioCategorias;

public class RepositorioCategoriasImpl implements RepositorioCategorias {

	
	private static RepositorioCategoriasImpl instancia = null;
	
	private BaseDeDatosImpl BD = BaseDeDatosImpl.getBD();
	
	public static RepositorioCategoriasImpl getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioCategoriasImpl();
		}
		
		return instancia; 
	}
	
	
	public RepositorioCategoriasImpl() {
		
		if(BaseDeDatosImpl.getBD() != null) {
			this.BD = BaseDeDatosImpl.getBD();
		}
		
		if(BD.getCategorias() != null) {
			this.categorias = (List<CategoriaImpl>) BD.getCategorias();
		}
		else {
			this.categorias = new ArrayList<CategoriaImpl>();
		}
		
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
		if(!categorias.contains(categoria)) {
			categorias.add(categoria);	
			BD.guardarFichero();
		}
	}

}
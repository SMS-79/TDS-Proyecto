
package umu.tds.gestor.repository.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import umu.tds.gestor.modelo.impl.Categoria;
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
			this.categorias = (List<Categoria>) BD.getCategorias();
		}
		else {
			this.categorias = new ArrayList<Categoria>();
		}
		
	}
	
	
	
	private List<Categoria> categorias = new ArrayList<Categoria>(); 
	
	@Override
	public List<? extends Categoria> getCategorias() {
		return Collections.unmodifiableList(categorias);
	}

	@Override
	public List<? extends Categoria> filtrarCategorias(String categoriaId) {
		return categorias.stream()
				.filter(c -> c.toString().startsWith(categoriaId))
				.toList();
	}

	@Override
	public void aniadirCategoria(Categoria categoria) {
		if(!categorias.contains(categoria)) {
			categorias.add(categoria);	
			BD.guardarFichero();
		}
	}

}
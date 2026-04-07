package umu.tds.gestor.repository.impl;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import umu.tds.gestor.modelo.impl.CarteraImpl;
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
	
	private void guardarFichero() {
	    try {
	      
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
	       
	        File fichero = new File("gastos.json");
	        
	        CarteraImpl cartera = CarteraImpl.getCartera();  
	        
	        mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, cartera);
	        
	    } catch (Exception e) {
	    	System.err.println("Error al guardar el fichero de gastos: " + e.getMessage()); 
	        e.printStackTrace();
	    }
	}
	
	private void cargarFichero() {
	    try {
	        File fichero = new File("gastos.json");
	        
	        if (fichero.exists()) {
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
	            
	            CarteraImpl carteraCargada = mapper.readValue(fichero, CarteraImpl.class);
	            
	            CarteraImpl.setCartera(carteraCargada);
	        }
	    } catch (Exception e) {
	        System.err.println("Error al cargar el fichero de gastos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	private List<CategoriaImpl> categorias = new ArrayList<CategoriaImpl>(); 
	
	private RepositorioCategoriasImpl() {
		cargarFichero();
		
		if(CarteraImpl.getCartera() != null && CarteraImpl.getCartera().getCategorias() != null) {
			this.categorias = (List<CategoriaImpl>) CarteraImpl.getCartera().getCategorias();
		}
		else {
			this.categorias = new ArrayList<CategoriaImpl>();
		}
	}
	
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
			guardarFichero();
		}
	}

}
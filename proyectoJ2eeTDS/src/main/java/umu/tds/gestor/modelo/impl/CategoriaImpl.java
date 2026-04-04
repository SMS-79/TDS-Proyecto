package umu.tds.gestor.modelo.impl;

import umu.tds.gestor.modelo.Categoria;

public class CategoriaImpl implements Categoria {
	
	private String nombre; //identificador de la categoría (atributo único)
	
	
	//contrusctor vacío que es necesario para luego añadir la persistencia en JSON
	public CategoriaImpl() {
	}
	
	public CategoriaImpl(String nombre) {
		this.nombre = nombre.trim(); //uso de trim para recibir cadena limpia de espacios
	}
	
	@Override
	public String getNombre() {
		return nombre; 
	}
	
	@Override
	public void setNombre(String nombre) {
		
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío."); 
		}
		this.nombre = nombre.trim(); //uso de trim para recibir cadena limpia de espacios
	}
	
	@Override
	public String toString() { 
		return this.nombre; // Para que luego salga el texto del campo en la lista de la vista principal
	}
	
	
}

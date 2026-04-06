package umu.tds.gestor.modelo.impl;

import java.util.Objects;

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


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaImpl other = (CategoriaImpl) obj;
		return Objects.equals(nombre, other.nombre);
	}
	
	
	
	
}

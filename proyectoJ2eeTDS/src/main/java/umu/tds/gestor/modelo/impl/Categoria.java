package umu.tds.gestor.modelo.impl;

import java.util.Objects;

public class Categoria{
	
	private String nombre; 
	
	public Categoria() {
	}
	
	public Categoria(String nombre) {
		this.nombre = nombre.trim(); 
	}
	
	
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
		Categoria other = (Categoria) obj;
		return Objects.equals(nombre, other.nombre);
	}
	
	
	
	
}

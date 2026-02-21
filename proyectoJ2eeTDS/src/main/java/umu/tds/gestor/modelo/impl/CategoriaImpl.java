package umu.tds.gestor.modelo.impl;

import umu.tds.gestor.modelo.Categoria;
<<<<<<< HEAD

public class CategoriaImpl implements Categoria{
=======
>>>>>>> 35c3bb57b310b68ea469759a565824f61269ae14

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
		/*trim se usa para ignorar los espacios, 
		 * así cubrimos el caso en el que se pueda introducir
		   una cadena en blanco que sean solo espacios*/
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío."); 
		}
		this.nombre = nombre.trim(); //uso de trim para recibir cadena limpia de espacios
	}
	
	
}

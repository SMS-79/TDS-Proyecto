package umu.tds.gestor.modelo;

import java.time.LocalDate;

import umu.tds.gestor.modelo.impl.CategoriaImpl;

public interface Gasto {

	CategoriaImpl getCategoria();
	
	void setCategoria(CategoriaImpl categoria);
	
	LocalDate getFecha();
	
	void setFecha(LocalDate fecha);
	
	double getCantidad();
	
	void setCantidad(double precio);
	
	void borrar();
	
}

package umu.tds.gestor.modelo;

import java.time.LocalDate;
import java.util.UUID;

import umu.tds.gestor.modelo.impl.Categoria;

public interface Gasto {

	UUID getIdGasto();

	Categoria getCategoria();
	
	void setCategoria(Categoria categoria);
	
	LocalDate getFecha();
	
	void setFecha(LocalDate fecha);
	
	double getCantidad();
	
	void setCantidad(double precio);
		
}

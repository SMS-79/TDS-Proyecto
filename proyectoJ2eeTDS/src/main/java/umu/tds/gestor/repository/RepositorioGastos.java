package umu.tds.gestor.repository;

import java.time.LocalDate;
import java.util.List;

import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;

public interface RepositorioGastos {

	List<? extends GastoImpl> getGastos();
	
	GastoImpl filtrarGasto();
	
	void crearGasto(double precio, LocalDate fecha, CategoriaImpl categoria);
	
	void borrarGasto(GastoImpl gasto);
	
	void cambiarCantidadGasto(GastoImpl gasto, double precio); // faltan parametros
	
	void cambiarFechaGasto(GastoImpl gasto, LocalDate fecha);
	
	void cambiarCategoriaGasto(GastoImpl gasto, CategoriaImpl categoria);
	
	
	
	
}

package umu.tds.gestor.repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import umu.tds.gestor.modelo.impl.CategoriaImpl;
import umu.tds.gestor.modelo.impl.GastoImpl;

public interface RepositorioGastos {

	List<? extends GastoImpl> getGastos();
	
	List<? extends GastoImpl> filtrarGasto(List<Month> meses, LocalDate fechaInicio, LocalDate fechaFin, List<? extends CategoriaImpl> categorias);
	
	void añadirGasto(GastoImpl gasto);
	
	void borrarGasto(GastoImpl gasto);
	
	void cambiarCantidadGasto(GastoImpl gasto, double precio); // faltan parametros
	
	void cambiarFechaGasto(GastoImpl gasto, LocalDate fecha);
	
	void cambiarCategoriaGasto(GastoImpl gasto, CategoriaImpl categoria);

}
